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

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection

/**
 * 力試しのコレクション.
 *
 * @param values 値のリスト
 */
data class ExamCollection(
    private val values: List<Exam>,
) {
    /**
     * すべての力試し.
     */
    val all: List<Exam> = values

    /**
     * 保持履歴の上限を超えた力試し.
     */
    val overflowed: List<Exam> by lazy {
        if (values.size <= MAX_HISTORY_COUNT) {
            return@lazy listOf<Exam>()
        }
        return@lazy values.takeLast(values.size - MAX_HISTORY_COUNT)
    }

    /**
     * 過去の力試しの結果から間違えた問題の歌の番号の集合.
     */
    val totalWrongKarutaNoCollection: KarutaNoCollection by lazy {
        KarutaNoCollection(
            values
                .fold(mutableSetOf<KarutaNo>()) { karutaNoSet, karutaExam ->
                    karutaNoSet.addAll(karutaExam.result.wrongKarutaNoCollection.values)
                    karutaNoSet
                }.toList(),
        )
    }

    companion object {
        private const val MAX_HISTORY_COUNT = 10
    }
}
