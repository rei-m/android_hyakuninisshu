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
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import java.util.*

class KarutaQuizRepositoryImpl(private val orma: OrmaDatabase) : KarutaQuizRepository {

    private val funcConvertEntity = Function<KarutaQuizSchema, KarutaQuiz> { karutaQuizSchema ->
        val karutaIdentifierList = ArrayList<KarutaIdentifier>()
        karutaQuizSchema.getChoices(orma)
            .selector()
            .executeAsObservable()
            .map { karutaQuizChoiceSchema -> KarutaIdentifier(karutaQuizChoiceSchema.karutaId.toInt()) }
            .subscribe { karutaIdentifierList.add(it) }

        if (karutaQuizSchema.startDate == null) {
            KarutaQuiz.createReady(KarutaQuizIdentifier(karutaQuizSchema.quizId),
                karutaIdentifierList,
                KarutaIdentifier(karutaQuizSchema.collectId.toInt()))
        } else {
            if (karutaQuizSchema.answerTime!! > 0) {
                KarutaQuiz.createAnswered(KarutaQuizIdentifier(karutaQuizSchema.quizId),
                    karutaIdentifierList,
                    KarutaIdentifier(karutaQuizSchema.collectId.toInt()),
                    karutaQuizSchema.startDate!!,
                    karutaQuizSchema.answerTime!!,
                    ChoiceNo.forValue(karutaQuizSchema.choiceNo!!),
                    karutaQuizSchema.isCollect)
            } else {
                KarutaQuiz.createInAnswer(KarutaQuizIdentifier(karutaQuizSchema.quizId),
                    karutaIdentifierList,
                    KarutaIdentifier(karutaQuizSchema.collectId.toInt()),
                    karutaQuizSchema.startDate!!)
            }
        }
    }

    override fun initialize(karutaQuizzes: KarutaQuizzes): Completable {
        return orma.transactionAsCompletable {

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

    override fun first(): Single<KarutaQuiz> {
        return KarutaQuizSchema.relation(orma)
            .where("answerTime = ?", 0)
            .selector()
            .executeAsObservable()
            .firstOrError()
            .map { karutaQuizSchema ->
                val karutaIdentifierList = ArrayList<KarutaIdentifier>()
                karutaQuizSchema.getChoices(orma)
                    .selector()
                    .executeAsObservable()
                    .map { karutaQuizChoiceSchema -> KarutaIdentifier(karutaQuizChoiceSchema.karutaId.toInt()) }
                    .subscribe { karutaIdentifierList.add(it) }
                KarutaQuiz.createReady(KarutaQuizIdentifier(karutaQuizSchema.quizId),
                    karutaIdentifierList,
                    KarutaIdentifier(karutaQuizSchema.collectId.toInt()))
            }
    }

    override fun findBy(identifier: KarutaQuizIdentifier): Single<KarutaQuiz> {
        return KarutaQuizSchema.relation(orma)
            .quizIdEq(identifier.value)
            .selector()
            .executeAsObservable()
            .firstOrError()
            .map(funcConvertEntity)
    }

    override fun store(karutaQuiz: KarutaQuiz): Completable {
        return orma.transactionAsCompletable {
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

    override fun list(): Single<KarutaQuizzes> {
        return KarutaQuizSchema.relation(orma)
            .selector()
            .executeAsObservable()
            .map(funcConvertEntity)
            .toList()
            .map { KarutaQuizzes(it) }
    }

    override fun countQuizByAnswered(): Single<KarutaQuizCounter> {
        val totalCountSingle = KarutaQuizSchema.relation(orma)
            .selector()
            .executeAsObservable()
            .count()

        val answeredCountSingle = KarutaQuizSchema.relation(orma)
            .where("startDate IS NOT NULL")
            .selector()
            .executeAsObservable()
            .count()

        return Single.zip(totalCountSingle, answeredCountSingle, BiFunction { totalCount, answeredCount ->
            KarutaQuizCounter(totalCount.toInt(), answeredCount.toInt())
        })
    }
}
