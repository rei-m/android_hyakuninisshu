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

package me.rei_m.hyakuninisshu.state.training.model

import android.content.res.Resources
import androidx.annotation.StringRes
import me.rei_m.hyakuninisshu.state.R

/**
 * 練習の取り札を覚える時間.
 */
enum class InputSecondCondition(
    val value: Int,
    @param:StringRes private val resId: Int
) : SelectableItem {
    NONE(0, R.string.input_second_none),
    SHORT(3, R.string.input_second_short),
    NORMAL(6, R.string.input_second_normal),
    LONG(9, R.string.input_second_long);

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
