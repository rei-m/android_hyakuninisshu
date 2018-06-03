/*
 * Copyright (c) 2017. Rei Matsushita
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
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import java.util.*

class KarutaExamRepositoryImpl(private val orma: OrmaDatabase) : KarutaExamRepository {

    override fun storeResult(karutaExamResult: KarutaExamResult,
                             tookExamDate: Date): Single<KarutaExamIdentifier> {

        var karutaExamSchemaId: Long = 0

        orma.transactionSync {
            val karutaExamSchema = KarutaExamSchema(
                    tookExamDate = tookExamDate,
                    totalQuizCount = karutaExamResult.quizCount,
                    averageAnswerTime = karutaExamResult.averageAnswerSec
            )
            karutaExamSchemaId = KarutaExamSchema.relation(orma).inserter().execute(karutaExamSchema)
            karutaExamSchema.id = karutaExamSchemaId

            val examWrongKarutaSchemaInserter = ExamWrongKarutaSchema.relation(orma).inserter()
            karutaExamResult.wrongKarutaIds.values.forEach {
                val examWrongKarutaSchema = ExamWrongKarutaSchema(SingleAssociation.just(karutaExamSchema), it.value.toLong())
                examWrongKarutaSchemaInserter.execute(examWrongKarutaSchema)
            }
        }

        return Single.just(KarutaExamIdentifier(karutaExamSchemaId))
    }

    override fun adjustHistory(historySize: Int): Completable {
        val karutaExamSchemaObservable = KarutaExamSchema.relation(orma)
                .selector()
                .orderByIdAsc()
                .executeAsObservable()

        val examCount = karutaExamSchemaObservable.count().blockingGet()

        return if (historySize < examCount) {
            orma.transactionAsCompletable {
                val deleter = KarutaExamSchema.relation(orma).deleter()
                karutaExamSchemaObservable
                        .take(examCount - historySize)
                        .subscribe { karutaExamSchema -> deleter.idEq(karutaExamSchema.id).execute() }
            }
        } else {
            Completable.complete()
        }
    }

    override fun findBy(identifier: KarutaExamIdentifier): Single<KarutaExam> {
        return KarutaExamSchema.relation(orma)
                .idEq(identifier.value)
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map { examSchema ->
                    val examWrongKarutaSchemaList = ArrayList<ExamWrongKarutaSchema>()
                    examSchema.getWrongKarutas(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe { examWrongKarutaSchemaList.add(it) }
                    KarutaExamFactory.create(examSchema, examWrongKarutaSchemaList)
                }
    }

    override fun list(): Single<KarutaExams> {
        return KarutaExamSchema.relation(orma)
                .selector()
                .orderByIdDesc()
                .executeAsObservable()
                .map { examSchema ->
                    val examWrongKarutaSchemaList = ArrayList<ExamWrongKarutaSchema>()
                    examSchema.getWrongKarutas(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe { examWrongKarutaSchemaList.add(it) }
                    KarutaExamFactory.create(examSchema, examWrongKarutaSchemaList)
                }.toList()
                .map { KarutaExams(it) }
    }
}
