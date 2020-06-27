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

package me.rei_m.hyakuninisshu.state.exam.model

import me.rei_m.hyakuninisshu.state.question.model.QuestionResult

/**
 * 力試し結果表示用.
 *
 * @param id 力試しID値
 * @param score 力試しの結果
 * @param averageAnswerSecText 力試しの全問題の平均解答時間(秒)
 * @param questionResultList 力試しの各問題の結果
 * @param fromNowText 現在時間から何秒前に実行したか
 */
data class ExamResult(
    val id: Long,
    val score: String,
    val averageAnswerSecText: String,
    val questionResultList: List<QuestionResult>,
    val fromNowText: String
)
