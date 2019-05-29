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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.infrastructure.database

import com.github.gfx.android.orma.SingleAssociation
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import java.util.*

class KarutaExamRepositoryImpl(private val orma: OrmaDatabase) : KarutaExamRepository {

    override fun storeResult(
        karutaExamResult: KarutaExamResult,
        tookExamDate: Date
    ): KarutaExamIdentifier {

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
                val examWrongKarutaSchema =
                    ExamWrongKarutaSchema(SingleAssociation.just(karutaExamSchema), it.value.toLong())
                examWrongKarutaSchemaInserter.execute(examWrongKarutaSchema)
            }
        }

        return KarutaExamIdentifier(karutaExamSchemaId)
    }

    override fun adjustHistory(historySize: Int) {
        val karutaExamSchemaList = KarutaExamSchema.relation(orma)
            .selector()
            .orderByIdAsc()
            .toList()

        val examCount = karutaExamSchemaList.size

        if (historySize < examCount) {
            orma.transactionSync {
                val deleter = KarutaExamSchema.relation(orma).deleter()
                karutaExamSchemaList.take(examCount - historySize).forEach {
                    deleter.idEq(it.id).execute()
                }
            }
        }
    }

    override fun findBy(karutaExamId: KarutaExamIdentifier): KarutaExam? {
        val examSchema = KarutaExamSchema.relation(orma)
            .idEq(karutaExamId.value)
            .selector()
            .firstOrNull() ?: return null

        val examWrongKarutaSchemaList = examSchema
            .getWrongKarutas(orma)
            .selector()
            .toList()

        return examSchema.toModel(examWrongKarutaSchemaList)
    }

    override fun list(): KarutaExams {
        val examSchemaList = KarutaExamSchema.relation(orma)
            .selector()
            .orderByIdDesc()
            .toList()

        val examList = examSchemaList.map { examSchema ->
            val examWrongKarutaSchemaList = examSchema
                .getWrongKarutas(orma)
                .selector()
                .toList()
            return@map examSchema.toModel(examWrongKarutaSchemaList)
        }

        return KarutaExams(examList)
    }
}

private fun KarutaExamSchema.toModel(examWrongKarutaSchemaList: List<ExamWrongKarutaSchema>): KarutaExam {

    val identifier = KarutaExamIdentifier(id)

    val wrongKarutaIdentifierList = examWrongKarutaSchemaList.map {
        KarutaIdentifier(it.karutaId.toInt())
    }

    val wrongKarutaIds = KarutaIds(wrongKarutaIdentifierList)

    val resultSummary = KarutaQuizzesResultSummary(
        totalQuizCount,
        totalQuizCount - wrongKarutaIds.size,
        averageAnswerTime
    )

    val result = KarutaExamResult(resultSummary, wrongKarutaIds)

    return KarutaExam(identifier, tookExamDate, result)
}
