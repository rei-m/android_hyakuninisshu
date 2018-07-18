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

package me.rei_m.hyakuninisshu.presentation.helper

import android.content.Context
import android.content.res.Resources

import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta

object KarutaDisplayHelper {

    fun convertNumberToString(context: Context, number: Int): String {

        if (number == Karuta.NUMBER_OF_KARUTA) {
            return context.getString(R.string.karuta_number, context.getString(R.string.hundred))
        }

        val resources = context.resources
        val numArray = resources.getStringArray(R.array.number)

        val doubleDigits = number / 10
        val singleDigits = number % 10

        val sb = StringBuilder()
        if (0 < doubleDigits) {
            if (1 < doubleDigits) {
                sb.append(numArray[doubleDigits - 1])
            }
            sb.append(resources.getString(R.string.ten))
        }

        if (0 < singleDigits) {
            sb.append(numArray[singleDigits - 1])
        }

        return context.getString(R.string.karuta_number, sb.toString())
    }

    fun convertKimarijiToString(context: Context, kimariji: Int): String {
        val resources = context.resources
        val kimarijiArray = resources.getStringArray(R.array.kimariji)
        return kimarijiArray[kimariji - 1]
    }
}
