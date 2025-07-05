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
import me.rei_m.hyakuninisshu.domain.question.model.*
import kotlin.coroutines.CoroutineContext

/**
 * 初期のDB設計ミスったままなのでここの中身はおかしい・・・
 */
class QuestionRepositoryImpl(
    private val database: AppDatabase,
    private val ioContext: CoroutineContext = Dispatchers.IO,
) : QuestionRepository {
    override suspend fun initialize(questionList: List<Question>) {
        val questionDataList = mutableListOf<KarutaQuestionData>()
        val choiceDataList = mutableListOf<KarutaQuestionChoiceData>()
        questionList.forEach { question ->
            KarutaQuestionData(
                id = question.identifier.value,
                no = question.no,
                correctKarutaNo = question.correctNo.value,
                startDate = null,
                selectedKarutaNo = null,
                isCorrect = null,
                answerTime = null,
            ).let {
                questionDataList.add(it)
            }

            question.choiceList.forEachIndexed { indexChoice, choice ->
                KarutaQuestionChoiceData(
                    id = null,
                    karutaQuestionId = question.identifier.value,
                    karutaNo = choice.value,
                    order = indexChoice + 1,
                ).let {
                    choiceDataList.add(it)
                }
            }
        }

        withContext(ioContext) {
            database
                .runInTransaction<Deferred<Unit>> {
                    return@runInTransaction async {
                        database.karutaQuestionDao().deleteAll()
                        database.karutaQuestionDao().insertKarutaQuestions(questionDataList)
                        database.karutaQuestionChoiceDao().insertKarutaQuestionChoices(choiceDataList)
                    }
                }.await()
        }
    }

    override suspend fun count(): Int =
        withContext(ioContext) {
            database.karutaQuestionDao().count()
        }

    override suspend fun findById(questionId: QuestionId): Question? {
        return withContext(ioContext) {
            val questionData =
                database.karutaQuestionDao().findById(questionId.value)
                    ?: return@withContext null
            val choiceDataList =
                database.karutaQuestionChoiceDao().findAllByKarutaQuestionId(questionId.value)
            val choiceList = choiceDataList.map { KarutaNo((it.karutaNo)) }
            val correctNo = KarutaNo(questionData.correctKarutaNo)
            val result =
                if (questionData.selectedKarutaNo != null &&
                    questionData.answerTime != null &&
                    questionData.isCorrect != null
                ) {
                    QuestionResult(
                        selectedKarutaNo = KarutaNo(questionData.selectedKarutaNo),
                        answerMillSec = questionData.answerTime,
                        judgement = QuestionJudgement(correctNo, isCorrect = questionData.isCorrect),
                    )
                } else {
                    null
                }
            return@withContext Question(
                id = questionId,
                no = questionData.no,
                choiceList = choiceList,
                correctNo = correctNo,
                state =
                    Question.State.create(
                        startDate = questionData.startDate,
                        result = result,
                    ),
            )
        }
    }

    override suspend fun findIdByNo(no: Int): QuestionId? =
        withContext(ioContext) {
            database.karutaQuestionDao().findIdByNo(no)?.let { QuestionId(it) }
        }

    override suspend fun findCollection(): QuestionCollection {
        return withContext(ioContext) {
            val choiceMap: HashMap<String, MutableList<KarutaNo>> = hashMapOf()
            database.karutaQuestionChoiceDao().findAll().forEach {
                if (choiceMap.containsKey(it.karutaQuestionId)) {
                    choiceMap[it.karutaQuestionId]!!.add(KarutaNo(it.karutaNo))
                } else {
                    choiceMap[it.karutaQuestionId] = mutableListOf(KarutaNo(it.karutaNo))
                }
            }

            return@withContext database
                .karutaQuestionDao()
                .findAll()
                .map {
                    val choiceList =
                        choiceMap[it.id]
                            ?: throw IllegalStateException("Question doesn't have choice list")
                    val correctNo = KarutaNo(it.correctKarutaNo)
                    val result =
                        if (it.selectedKarutaNo != null && it.answerTime != null && it.isCorrect != null) {
                            QuestionResult(
                                selectedKarutaNo = KarutaNo(it.selectedKarutaNo),
                                answerMillSec = it.answerTime,
                                judgement = QuestionJudgement(correctNo, isCorrect = it.isCorrect),
                            )
                        } else {
                            null
                        }

                    return@map Question(
                        id = QuestionId(it.id),
                        no = it.no,
                        choiceList = choiceList,
                        correctNo = correctNo,
                        state =
                            Question.State.create(
                                startDate = it.startDate,
                                result = result,
                            ),
                    )
                }.let {
                    QuestionCollection(it)
                }
        }
    }

    override suspend fun save(question: Question) {
        val karutaQuestionData =
            when (val questionState = question.state) {
                is Question.State.Ready -> {
                    KarutaQuestionData(
                        id = question.identifier.value,
                        no = question.no,
                        correctKarutaNo = question.correctNo.value,
                        startDate = null,
                        selectedKarutaNo = null,
                        isCorrect = null,
                        answerTime = null,
                    )
                }

                is Question.State.InAnswer -> {
                    KarutaQuestionData(
                        id = question.identifier.value,
                        no = question.no,
                        correctKarutaNo = question.correctNo.value,
                        startDate = questionState.startDate,
                        selectedKarutaNo = null,
                        isCorrect = null,
                        answerTime = null,
                    )
                }

                is Question.State.Answered -> {
                    KarutaQuestionData(
                        id = question.identifier.value,
                        no = question.no,
                        correctKarutaNo = question.correctNo.value,
                        startDate = questionState.startDate,
                        selectedKarutaNo = questionState.result.selectedKarutaNo.value,
                        isCorrect = questionState.result.judgement.isCorrect,
                        answerTime = questionState.result.answerMillSec,
                    )
                }
            }

        withContext(ioContext) {
            database.karutaQuestionDao().updateKarutaQuestion(karutaQuestionData)
        }
    }
}
