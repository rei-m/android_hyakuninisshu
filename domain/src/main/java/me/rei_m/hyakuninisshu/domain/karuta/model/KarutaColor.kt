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

package me.rei_m.hyakuninisshu.domain.karuta.model

import me.rei_m.hyakuninisshu.domain.ValueObject

/**
 * 歌の色.
 *
 * @param value 色の値
 */
enum class KarutaColor(
    val value: String,
) : ValueObject {
    BLUE("blue"),
    PINK("pink"),
    YELLOW("yellow"),
    GREEN("green"),
    ORANGE("orange"),
    ;

    override fun toString(): String = "Color(value='$value')"

    companion object {
        fun forValue(value: String): KarutaColor =
            values().find { it.value == value } ?: let {
                throw AssertionError("no enum found. value is $value")
            }
    }
}
