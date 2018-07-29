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

package me.rei_m.hyakuninisshu.domain.model.quiz

import me.rei_m.hyakuninisshu.domain.ValueObject
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds

/**
 * 百人一首力試しの結果.
 */
data class KarutaExamResult(private val resultSummary: KarutaQuizzesResultSummary,
                            val wrongKarutaIds: KarutaIds) : ValueObject {

    val judgements: List<KarutaQuizJudgement>

    val quizCount = resultSummary.quizCount

    val score = resultSummary.score

    val averageAnswerSec = resultSummary.averageAnswerSec

    init {
        this.judgements = (1..Karuta.NUMBER_OF_KARUTA).map {
            val karutaId = KarutaIdentifier(it)
            val isCorrect = !wrongKarutaIds.contains(karutaId)
            KarutaQuizJudgement(karutaId, isCorrect)
        }
    }
}
