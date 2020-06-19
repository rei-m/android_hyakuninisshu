/*
 * Copyright (c) 2020. Rei Matsushita
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

import com.github.gfx.android.orma.SingleAssociation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import me.rei_m.hyakuninisshu.domain.question.model.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class ExamRepositoryImpl(
    private val orma: OrmaDatabase,
    private val ioContext: CoroutineContext = Dispatchers.IO
) : ExamRepository {
    override suspend fun findById(examId: ExamId): Exam? {
        return withContext(ioContext) {
            val examSchema = KarutaExamSchema.relation(orma)
                .idEq(examId.value)
                .selector()
                .firstOrNull() ?: return@withContext null

            val examWrongKarutaSchemaList = examSchema
                .getWrongKarutas(orma)
                .selector()
                .toList()

            examSchema.toModel(examWrongKarutaSchemaList)
        }
    }

    override suspend fun last(): Exam? {
        return withContext(ioContext) {
            val examSchema = KarutaExamSchema.relation(orma)
                .selector()
                .orderByIdDesc()
                .firstOrNull() ?: return@withContext null

            val examWrongKarutaSchemaList = examSchema
                .getWrongKarutas(orma)
                .selector()
                .toList()

            examSchema.toModel(examWrongKarutaSchemaList)
        }
    }

    override suspend fun findCollection(): ExamCollection {
        return withContext(ioContext) {
            val examSchemaList = KarutaExamSchema.relation(orma)
                .selector()
                .orderByIdDesc()
                .toList()
            return@withContext examSchemaList.map {
                val examWrongKarutaSchemaList = it
                    .getWrongKarutas(orma)
                    .selector()
                    .toList()
                it.toModel(examWrongKarutaSchemaList)
            }.let {
                ExamCollection(it)
            }
        }
    }

    override suspend fun add(
        examResult: ExamResult,
        tookExamDate: Date
    ): ExamId = withContext(ioContext) {
        var karutaExamSchemaId: Long = 0

        orma.transactionSync {
            val karutaExamSchema = KarutaExamSchema(
                tookExamDate = tookExamDate,
                totalQuizCount = examResult.resultSummary.totalQuestionCount,
                averageAnswerTime = examResult.resultSummary.averageAnswerSec
            )
            karutaExamSchemaId =
                KarutaExamSchema.relation(orma).inserter().execute(karutaExamSchema)
            karutaExamSchema.id = karutaExamSchemaId

            val examWrongKarutaSchemaInserter = ExamWrongKarutaSchema.relation(orma).inserter()
            examResult.wrongKarutaNoCollection.values.forEach {
                val examWrongKarutaSchema =
                    ExamWrongKarutaSchema(
                        SingleAssociation.just(karutaExamSchema),
                        it.value.toLong()
                    )
                examWrongKarutaSchemaInserter.execute(examWrongKarutaSchema)
            }
        }

        ExamId(karutaExamSchemaId)
    }

    override suspend fun deleteList(list: List<Exam>) = withContext(ioContext) {
        orma.transactionSync {
            val deleter = KarutaExamSchema.relation(orma).deleter()
            list.forEach {
                deleter.idEq(it.identifier.value).execute()
            }
        }
    }
}

private fun KarutaExamSchema.toModel(examWrongKarutaSchemaList: List<ExamWrongKarutaSchema>): Exam {

    val identifier = ExamId(id)

    val wrongKarutaNoList = examWrongKarutaSchemaList.map {
        KarutaNo(it.karutaId.toInt())
    }

    val wrongKarutaNoCollection = KarutaNoCollection(wrongKarutaNoList)

    val resultSummary = QuestionResultSummary(
        totalQuizCount,
        totalQuizCount - wrongKarutaNoCollection.size,
        averageAnswerTime
    )

    val result = ExamResult(resultSummary, wrongKarutaNoCollection)

    return Exam(identifier, tookExamDate, result)
}
