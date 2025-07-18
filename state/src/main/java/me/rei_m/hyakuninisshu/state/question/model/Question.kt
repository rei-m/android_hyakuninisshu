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

/**
 * 問題の表示用.
 *
 * @param id 問題のID値
 * @param no 問題の番号
 * @param position 問題が何問中何番目にいるかのテキスト
 * @param toriFudaList 取り札のリスト
 * @param yomiFuda 読み札
 */
data class Question(
    val id: String,
    val no: Int,
    val position: String,
    val toriFudaList: List<ToriFuda>,
    val yomiFuda: YomiFuda,
)
