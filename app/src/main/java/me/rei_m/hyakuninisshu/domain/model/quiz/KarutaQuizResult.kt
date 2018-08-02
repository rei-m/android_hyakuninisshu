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

package me.rei_m.hyakuninisshu.domain.model.quiz

import me.rei_m.hyakuninisshu.domain.ValueObject
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier

/**
 * 問題の解答結果.
 */
data class KarutaQuizResult(
        private val correctKarutaId: KarutaIdentifier,
        val choiceNo: ChoiceNo,
        private val isCorrect: Boolean,
        val answerMillSec: Long
) : ValueObject {
    val judgement: KarutaQuizJudgement = KarutaQuizJudgement(correctKarutaId, isCorrect)
}
