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
import java.util.regex.Pattern

private val pattern = Pattern.compile("^(?!000)(0\\d\\d|001|100)$")

/**
 * 歌の画像番号.
 *
 * @param value 値
 *
 * @throws IllegalArgumentException
 */
data class KarutaImageNo @Throws(IllegalArgumentException::class) constructor(
    val value: String
) : ValueObject {

    init {
        if (!pattern.matcher(value).matches()) {
            throw IllegalArgumentException("ImageNo is Invalid, value is $value")
        }
    }
}
