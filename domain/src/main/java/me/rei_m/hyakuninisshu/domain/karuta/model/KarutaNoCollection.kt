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

package me.rei_m.hyakuninisshu.domain.karuta.model

import me.rei_m.hyakuninisshu.domain.util.generateRandomIndexArray

/**
 * 歌番号のコレクション.
 *
 * @param values 値のリスト
 */
data class KarutaNoCollection(
    val values: List<KarutaNo>
) {

    /**
     * @return 保持している歌の数
     */
    val size: Int = values.size

    /**
     * @return ランダムにソートされた歌番号のリスト
     */
    val asRandomized: List<KarutaNo>
        get() = generateRandomIndexArray(values.size, values.size).map { values[it] }

    /**
     * 指定の歌IDを含んでいるか確認する.
     *
     * @param karutaNo 確認対象の歌番号
     * @return `true` 含んでいる場合, `false` 含んでいない場合
     */
    operator fun contains(karutaNo: KarutaNo): Boolean = values.contains(karutaNo)
}
