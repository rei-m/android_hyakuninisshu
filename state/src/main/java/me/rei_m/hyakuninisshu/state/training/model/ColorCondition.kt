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

package me.rei_m.hyakuninisshu.state.training.model

import android.content.res.Resources
import androidx.annotation.StringRes
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaColor
import me.rei_m.hyakuninisshu.state.R

/**
 * 練習の開始条件/色
 */
enum class ColorCondition(
    val value: KarutaColor?,
    @param:StringRes private val resId: Int,
) : SelectableItem {
    ALL(null, R.string.color_not_select),
    BLUE(KarutaColor.BLUE, R.string.color_blue),
    PINK(KarutaColor.PINK, R.string.color_pink),
    YELLOW(KarutaColor.YELLOW, R.string.color_yellow),
    GREEN(KarutaColor.GREEN, R.string.color_green),
    ORANGE(KarutaColor.ORANGE, R.string.color_orange),
    ;

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
