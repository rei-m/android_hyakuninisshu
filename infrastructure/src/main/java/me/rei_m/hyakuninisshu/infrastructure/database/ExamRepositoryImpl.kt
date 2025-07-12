/*
 * Copyright (c) 2025. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu.infrastructure.database

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import me.rei_m.hyakuninisshu.domain.question.model.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class ExamRepositoryImpl(
    private val database: AppDatabase,
    private val ioContext: CoroutineContext = Dispatchers.IO,
) : ExamRepository {
    override suspend fun findById(examId: ExamId): Exam? {
        return withContext(ioContext) {
            val karutaExamData =
                database.karutaExamDao().findById(examId.value) ?: return@withContext null
            return@withContext dataToModel(karutaExamData)
        }
    }

    override suspend fun last(): Exam? {
        return withContext(ioContext) {
            val karutaExamData = database.karutaExamDao().last() ?: return@withContext null
            return@withContext dataToModel(karutaExamData)
        }
    }

    override suspend fun findCollection(): ExamCollection {
        return withContext(ioContext) {
            val karutaExamDataList = database.karutaExamDao().findAll()
            val wrongKarutaNoDataList = database.karutaExamWrongKarutaNoDao().findAll()
            val wrongKarutaNoDataMap: HashMap<Long, MutableList<KarutaNo>> = hashMapOf()
            wrongKarutaNoDataList.forEach {
                if (wrongKarutaNoDataMap.containsKey(it.karutaExamId)) {
                    wrongKarutaNoDataMap[it.karutaExamId]!!.add(KarutaNo(it.karutaNo))
                } else {
                    wrongKarutaNoDataMap[it.karutaExamId] = mutableListOf(KarutaNo(it.karutaNo))
                }
            }

            return@withContext karutaExamDataList
                .map { karutaExamData ->
                    val id = ExamId(karutaExamData.id!!)
                    val wrongKarutaNoCollection =
                        KarutaNoCollection(
                            wrongKarutaNoDataMap[karutaExamData.id] ?: listOf(),
                        )
                    val resultSummary =
                        QuestionResultSummary(
                            totalQuestionCount = karutaExamData.totalQuestionCount,
                            correctCount = karutaExamData.totalQuestionCount - wrongKarutaNoCollection.size,
                            averageAnswerSec = karutaExamData.averageAnswerTime,
                        )
                    return@map Exam(
                        id = id,
                        tookDate = karutaExamData.tookExamDate,
                        result =
                            ExamResult(
                                resultSummary = resultSummary,
                                wrongKarutaNoCollection = wrongKarutaNoCollection,
                            ),
                    )
                }.let {
                    ExamCollection(it)
                }
        }
    }

    override suspend fun add(
        examResult: ExamResult,
        tookExamDate: Date,
    ): ExamId =
        withContext(ioContext) {
            return@withContext database.runInTransaction<Deferred<ExamId>> {
                return@runInTransaction async {
                    val karutaExamData =
                        KarutaExamData(
                            id = null,
                            tookExamDate = tookExamDate,
                            totalQuestionCount = examResult.resultSummary.totalQuestionCount,
                            averageAnswerTime = examResult.resultSummary.averageAnswerSec,
                        )
                    val karutaExamDataId = database.karutaExamDao().insert(karutaExamData)
                    database
                        .karutaExamWrongKarutaNoDao()
                        .insert(
                            examResult.wrongKarutaNoCollection.values.map {
                                KarutaExamWrongKarutaNoData(
                                    id = null,
                                    karutaExamId = karutaExamDataId,
                                    karutaNo = it.value,
                                )
                            },
                        )
                    return@async ExamId(
                        karutaExamDataId,
                    )
                }
            }
        }.await()

    override suspend fun deleteList(list: List<Exam>) =
        withContext(ioContext) {
            withContext(ioContext) {
                list
                    .map {
                        KarutaExamData(
                            id = it.identifier.value,
                            tookExamDate = it.tookDate,
                            totalQuestionCount = it.result.resultSummary.totalQuestionCount,
                            averageAnswerTime = it.result.resultSummary.averageAnswerSec,
                        )
                    }.let {
                        database.karutaExamDao().deleteExams(it)
                    }
            }
        }

    private fun dataToModel(karutaExamData: KarutaExamData): Exam {
        val wrongKarutaNoDataList =
            database.karutaExamWrongKarutaNoDao().findAllWithExamId(
                karutaExamId = karutaExamData.id!!,
            )
        return Exam(
            id = ExamId(karutaExamData.id),
            tookDate = karutaExamData.tookExamDate,
            result =
                ExamResult(
                    resultSummary =
                        QuestionResultSummary(
                            totalQuestionCount = karutaExamData.totalQuestionCount,
                            correctCount = karutaExamData.totalQuestionCount - wrongKarutaNoDataList.size,
                            averageAnswerSec = karutaExamData.averageAnswerTime,
                        ),
                    wrongKarutaNoCollection =
                        KarutaNoCollection(
                            wrongKarutaNoDataList.map {
                                KarutaNo(
                                    it.karutaNo,
                                )
                            },
                        ),
                ),
        )
    }
}
