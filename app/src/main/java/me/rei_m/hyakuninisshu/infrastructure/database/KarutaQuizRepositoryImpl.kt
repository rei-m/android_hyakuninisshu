/*
 * Copyright (c) 2018. Rei Matsushita
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
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.*

class KarutaQuizRepositoryImpl(private val orma: OrmaDatabase) : KarutaQuizRepository {

    override fun initialize(karutaQuizzes: KarutaQuizzes) {
        orma.transactionSync {

            KarutaQuizSchema.relation(orma).deleter().execute()
            KarutaQuizChoiceSchema.relation(orma).deleter().execute()

            val karutaQuizSchemaInserter = KarutaQuizSchema.relation(orma).inserter()
            val karutaQuizChoiceSchemaInserter = KarutaQuizChoiceSchema.relation(orma).inserter()

            for (karutaQuiz in karutaQuizzes.values) {
                val karutaQuizSchema = KarutaQuizSchema(
                    quizId = karutaQuiz.identifier().value,
                    collectId = karutaQuiz.correctId.value.toLong()
                )
                karutaQuizSchema.id = karutaQuizSchemaInserter.execute(karutaQuizSchema)
                karutaQuiz.choiceList.forEachIndexed { index, karutaIdentifier ->
                    val karutaQuizChoiceSchema = KarutaQuizChoiceSchema(
                        karutaQuizSchema = SingleAssociation.just(karutaQuizSchema),
                        karutaId = karutaIdentifier.value.toLong(),
                        orderNo = index.toLong()
                    )
                    karutaQuizChoiceSchemaInserter.execute(karutaQuizChoiceSchema)
                }
            }
        }
    }

    override fun first(): KarutaQuiz? {
        return KarutaQuizSchema.relation(orma)
            .where("answerTime = ?", 0)
            .selector()
            .firstOrNull()?.let {
                val karutaIdList = it.getChoices(orma)
                    .selector()
                    .map { choice -> KarutaIdentifier(choice.karutaId.toInt()) }
                    .toList()
                KarutaQuiz.createReady(
                    KarutaQuizIdentifier(it.quizId),
                    karutaIdList,
                    KarutaIdentifier(it.collectId.toInt())
                )
            }
    }

    override fun findBy(identifier: KarutaQuizIdentifier): KarutaQuiz? {
        return KarutaQuizSchema.relation(orma)
            .quizIdEq(identifier.value)
            .selector()
            .firstOrNull()?.let {
                convertEntity(it)
            }
    }

    override fun store(karutaQuiz: KarutaQuiz) {
        orma.transactionSync {
            val updater = KarutaQuizSchema.relation(orma)
                .quizIdEq(karutaQuiz.identifier().value)
                .updater()

            updater.startDate(karutaQuiz.startDate)
            val result = karutaQuiz.result
            if (result != null) {
                updater.isCollect(result.judgement.isCorrect)
                    .choiceNo(result.choiceNo.value)
                    .answerTime(result.answerMillSec)
            }
            updater.execute()
        }
    }

    override fun list(): KarutaQuizzes {
        return KarutaQuizzes(KarutaQuizSchema.relation(orma)
            .selector()
            .map { convertEntity(it) }
            .toList())
    }

    override fun countQuizByAnswered(): KarutaQuizCounter {
        val totalCount = KarutaQuizSchema.relation(orma)
            .selector()
            .count()

        val answeredCount = KarutaQuizSchema.relation(orma)
            .where("startDate IS NOT NULL")
            .selector()
            .count()

        return KarutaQuizCounter(totalCount, answeredCount)
    }


    private fun convertEntity(karutaQuizSchema: KarutaQuizSchema): KarutaQuiz {
        val karutaIdList = karutaQuizSchema
            .getChoices(orma)
            .map { KarutaIdentifier(it.karutaId.toInt()) }
        if (karutaQuizSchema.startDate == null) {
            return KarutaQuiz.createReady(
                KarutaQuizIdentifier(karutaQuizSchema.quizId),
                karutaIdList,
                KarutaIdentifier(karutaQuizSchema.collectId.toInt())
            )
        }
        return if (karutaQuizSchema.answerTime!! > 0) {
            KarutaQuiz.createAnswered(
                KarutaQuizIdentifier(karutaQuizSchema.quizId),
                karutaIdList,
                KarutaIdentifier(karutaQuizSchema.collectId.toInt()),
                karutaQuizSchema.startDate!!,
                karutaQuizSchema.answerTime!!,
                ChoiceNo.forValue(karutaQuizSchema.choiceNo!!),
                karutaQuizSchema.isCollect
            )
        } else {
            KarutaQuiz.createInAnswer(
                KarutaQuizIdentifier(karutaQuizSchema.quizId),
                karutaIdList,
                KarutaIdentifier(karutaQuizSchema.collectId.toInt()),
                karutaQuizSchema.startDate!!
            )
        }
    }
}
