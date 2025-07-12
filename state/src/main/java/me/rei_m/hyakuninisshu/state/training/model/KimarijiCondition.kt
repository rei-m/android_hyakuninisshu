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
import me.rei_m.hyakuninisshu.domain.karuta.model.Kimariji
import me.rei_m.hyakuninisshu.state.R

/**
 * 練習の条件/決まり字.
 */
enum class KimarijiCondition(
    val value: Kimariji?,
    @param:StringRes private val resId: Int,
) : SelectableItem {
    ALL(null, R.string.kimariji_not_select),
    ONE(Kimariji.ONE, R.string.kimariji_1),
    TWO(Kimariji.TWO, R.string.kimariji_2),
    THREE(Kimariji.THREE, R.string.kimariji_3),
    FOUR(Kimariji.FOUR, R.string.kimariji_4),
    FIVE(Kimariji.FIVE, R.string.kimariji_5),
    SIX(Kimariji.SIX, R.string.kimariji_6),
    ;

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int): KimarijiCondition = values()[ordinal]
    }
}
