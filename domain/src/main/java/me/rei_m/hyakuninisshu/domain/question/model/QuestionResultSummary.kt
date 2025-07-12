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

package me.rei_m.hyakuninisshu.domain.question.model

import me.rei_m.hyakuninisshu.domain.ValueObject

/**
 * 問題全体の解答結果集計.
 *
 * @param totalQuestionCount 全体の件数
 * @param correctCount 正解数
 * @param averageAnswerSec 平均解答時間
 */
data class QuestionResultSummary(
    val totalQuestionCount: Int,
    val correctCount: Int,
    val averageAnswerSec: Float,
) : ValueObject {
    val score = "$correctCount / $totalQuestionCount"
}
