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
import me.rei_m.hyakuninisshu.domain.question.model.*
import kotlin.coroutines.CoroutineContext

/**
 * 初期のDB設計ミスったままなのでここの中身はおかしい・・・
 */
class QuestionRepositoryImpl(
    private val orma: OrmaDatabase,
    private val ioContext: CoroutineContext = Dispatchers.IO
) : QuestionRepository {
    override suspend fun initialize(questionList: List<Question>) = withContext(ioContext) {
        orma.transactionSync {
            KarutaQuizSchema.relation(orma).deleter().execute()
            KarutaQuizChoiceSchema.relation(orma).deleter().execute()

            val karutaQuizSchemaInserter = KarutaQuizSchema.relation(orma).inserter()
            val karutaQuizChoiceSchemaInserter = KarutaQuizChoiceSchema.relation(orma).inserter()

            questionList.forEach { question ->
                val karutaQuizSchema = KarutaQuizSchema(
                    quizId = question.identifier.value,
                    collectId = question.correctNo.value.toLong(),
                    no = question.no
                )
                karutaQuizSchema.id = karutaQuizSchemaInserter.execute(karutaQuizSchema)
                question.choiceList.forEachIndexed { choiceOrder, karutaNo ->
                    val karutaQuizChoiceSchema = KarutaQuizChoiceSchema(
                        karutaQuizSchema = SingleAssociation.just(karutaQuizSchema),
                        karutaId = karutaNo.value.toLong(),
                        orderNo = choiceOrder.toLong()
                    )
                    karutaQuizChoiceSchemaInserter.execute(karutaQuizChoiceSchema)
                }
            }
        }
    }

    override suspend fun count(): Int = withContext(ioContext) {
        KarutaQuizSchema.relation(orma)
            .selector()
            .count()
    }

    override suspend fun findById(questionId: QuestionId): Question? = withContext(ioContext) {
        KarutaQuizSchema.relation(orma)
            .quizIdEq(questionId.value)
            .selector()
            .firstOrNull()?.let {
                val karutaIdList = it.getChoices(orma)
                    .selector()
                    .toList()
                it.toModel(karutaIdList)
            }
    }

    override suspend fun findIdByNo(no: Int): QuestionId? = withContext(ioContext) {
        KarutaQuizSchema.relation(orma)
            .where("no = ?", no)
            .selector()
            .firstOrNull()?.let {
                QuestionId(it.quizId)
            }
    }

    override suspend fun findCollection(): QuestionCollection {
        return withContext(ioContext) {
            val choiceMap: HashMap<Long, MutableList<KarutaNo>> = hashMapOf()
            KarutaQuizChoiceSchema.relation(orma).selector().forEach {
                if (choiceMap.containsKey(it.karutaQuizSchema.id)) {
                    choiceMap[it.karutaQuizSchema.id]!!.add(KarutaNo(it.karutaId.toInt()))
                } else {
                    choiceMap[it.karutaQuizSchema.id] = mutableListOf(KarutaNo(it.karutaId.toInt()))
                }
            }

            return@withContext KarutaQuizSchema.relation(orma).selector().orderByIdAsc().map {
                val choiceList = choiceMap[it.id]
                    ?: throw IllegalStateException("Question doesn't have choice list")
                val correctNo = KarutaNo(it.collectId.toInt())
                val result: QuestionResult? = when {
                    it.answerTime!! > 0 -> {
                        QuestionResult(
                            selectedKarutaNo = KarutaNo(it.choiceNo!!),
                            answerMillSec = it.answerTime!!,
                            judgement = QuestionJudgement(correctNo, isCorrect = it.isCollect)
                        )
                    }
                    else -> {
                        null
                    }
                }

                return@map Question(
                    id = QuestionId(it.quizId),
                    no = if (it.no == null) 0 else it.no!!,
                    choiceList = choiceList,
                    correctNo = correctNo,
                    state = Question.State.create(
                        startDate = it.startDate,
                        result = result
                    )
                )
            }.let {
                QuestionCollection(it)
            }
        }
    }

    override suspend fun save(question: Question) = withContext(ioContext) {
        orma.transactionSync {
            val updater = KarutaQuizSchema.relation(orma)
                .quizIdEq(question.identifier.value)
                .updater()
            when (val state = question.state) {
                is Question.State.InAnswer -> {
                    updater.startDate(state.startDate)
                }
                is Question.State.Answered -> {
                    updater.isCollect(state.result.judgement.isCorrect)
                        .choiceNo(state.result.selectedKarutaNo.value)
                        .answerTime(state.result.answerMillSec)
                }
            }
            updater.execute()
        }
    }
}

private fun KarutaQuizSchema.toModel(choiceSchemaList: List<KarutaQuizChoiceSchema>): Question {
    val questionId = QuestionId(quizId)
    val choiceList = choiceSchemaList.map { KarutaNo(it.karutaId.toInt()) }
    val correctNo = KarutaNo(collectId.toInt())
    val result: QuestionResult? = when {
        answerTime!! > 0 -> {
            QuestionResult(
                selectedKarutaNo = KarutaNo(choiceNo!!),
                answerMillSec = answerTime!!,
                judgement = QuestionJudgement(correctNo, isCorrect = isCollect)
            )
        }
        else -> {
            null
        }
    }

    val questionState: Question.State = Question.State.create(startDate, result)

    return Question(
        id = questionId,
        no = if (no == null) 0 else no!!,
        choiceList = choiceList,
        correctNo = correctNo,
        state = questionState
    )
}