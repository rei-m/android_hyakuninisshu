/*
 * Copyright (c) 2019. Rei Matsushita
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

package me.rei_m.hyakuninisshu.feature.corecomponent.enums

import android.content.res.Resources
import androidx.annotation.StringRes
import me.rei_m.hyakuninisshu.feature.corecomponent.R

enum class QuizAnimationSpeed(
    val value: Long?,
    @param:StringRes private val resId: Int
) : SpinnerItem {
    NONE(null, R.string.animation_none),
    SLOW(1000, R.string.animation_slow),
    NORMAL(500, R.string.animation_normal),
    FAST(300, R.string.animation_fast);

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int): QuizAnimationSpeed = values()[ordinal]
    }
}