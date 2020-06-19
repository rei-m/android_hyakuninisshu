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

import me.rei_m.hyakuninisshu.domain.ValueObject

/**
 * 歌の番号.
 *
 * @param value 値
 *
 * @throws IllegalArgumentException
 */
data class KarutaNo @Throws(IllegalArgumentException::class) constructor(
    val value: Int
) : ValueObject {

    init {
        if (value < MIN_VALUE || MAX_VALUE < value) {
            throw IllegalArgumentException("KarutaNo is Invalid, value is $value")
        }
    }

    companion object {
        private const val MIN_VALUE = 1
        private const val MAX_VALUE = 100
        val MIN = KarutaNo(MIN_VALUE)
        val MAX = KarutaNo(MAX_VALUE)
        val LIST = (MIN.value..MAX.value).map { KarutaNo(it) }
    }
}
