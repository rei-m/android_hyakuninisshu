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

package me.rei_m.hyakuninisshu.state.material.model

import androidx.annotation.StringRes
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaColor
import me.rei_m.hyakuninisshu.state.R

/**
 * 資料の色で絞り込むフィルター.
 *
 * @param value 色の値
 * @param resId テキスト表示用のID
 */
enum class ColorFilter(
    val value: KarutaColor?,
    @param:StringRes val resId: Int
) {
    ALL(null, R.string.color_not_select),
    BLUE(KarutaColor.BLUE, R.string.color_blue),
    PINK(KarutaColor.PINK, R.string.color_pink),
    YELLOW(KarutaColor.YELLOW, R.string.color_yellow),
    GREEN(KarutaColor.GREEN, R.string.color_green),
    ORANGE(KarutaColor.ORANGE, R.string.color_orange);

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
