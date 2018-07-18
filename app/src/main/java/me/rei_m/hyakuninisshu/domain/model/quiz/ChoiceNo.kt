/*
 * Copyright (c) 2017. Rei Matsushita
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

/**
 * 問題の選択肢.
 */
enum class ChoiceNo(val value: Int) : ValueObject {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    val asIndex = value - 1

    override fun toString() = "ChoiceNo(value=$value)"

    companion object {
        fun forValue(value: Int): ChoiceNo = values().find { it.value == value } ?: let {
            throw AssertionError("no enum found. value is $value")
        }
    }
}
