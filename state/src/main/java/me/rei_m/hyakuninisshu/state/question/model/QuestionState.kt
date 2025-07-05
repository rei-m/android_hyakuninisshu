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

package me.rei_m.hyakuninisshu.state.question.model

import me.rei_m.hyakuninisshu.state.material.model.Material

/**
 * 問題の状態表示用.
 */
sealed class QuestionState {
    /**
     * 回答開始前.
     */
    object Ready : QuestionState()

    /**
     * 回答中.
     */
    object InAnswer : QuestionState()

    /**
     * 回答済.
     *
     * @param selectedToriFudaIndex 何番目の取り札をとったか
     * @param isCorrect 正解したか
     * @param correctMaterial 問題の正解の歌の資料
     * @param nextQuestionId 次の問題のID値
     */
    class Answered(
        val selectedToriFudaIndex: Int,
        val isCorrect: Boolean,
        val correctMaterial: Material,
        val nextQuestionId: String?,
    ) : QuestionState()
}
