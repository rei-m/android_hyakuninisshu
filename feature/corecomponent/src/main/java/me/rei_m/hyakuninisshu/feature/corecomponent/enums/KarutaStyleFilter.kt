/*
 * Copyright (c) 2018. Rei Matsushita
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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.feature.corecomponent.enums

import android.content.res.Resources
import androidx.annotation.StringRes
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaStyle
import me.rei_m.hyakuninisshu.feature.corecomponent.R

enum class KarutaStyleFilter(
    val value: KarutaStyle,
    @param:StringRes private val resId: Int
) : SpinnerItem {
    KANA(KarutaStyle.KANA, R.string.display_style_kana),
    KANJI(KarutaStyle.KANJI, R.string.display_style_kanji);

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
