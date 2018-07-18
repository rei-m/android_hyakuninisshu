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

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzesResultSummary

object KarutaExamFactory {
    fun create(schema: KarutaExamSchema,
               examWrongKarutaSchemaList: List<ExamWrongKarutaSchema>): KarutaExam {

        val identifier = KarutaExamIdentifier(schema.id)

        val wrongKarutaIdentifierList = examWrongKarutaSchemaList.map { KarutaIdentifier(it.karutaId.toInt()) }

        val wrongKarutaIds = KarutaIds(wrongKarutaIdentifierList)

        val resultSummary = KarutaQuizzesResultSummary(
                schema.totalQuizCount,
                schema.totalQuizCount - wrongKarutaIds.size,
                schema.averageAnswerTime
        )

        val result = KarutaExamResult(resultSummary, wrongKarutaIds)

        return KarutaExam(identifier, result)
    }
}
