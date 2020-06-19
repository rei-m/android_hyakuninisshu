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
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.state.R

/**
 * 練習の条件/出題範囲開始.
 */
enum class RangeFromCondition(
    val value: KarutaNo,
    @param:StringRes private val resId: Int
) : SelectableItem {
    ONE(KarutaNo(1), R.string.training_range_1),
    ELEVEN(KarutaNo(11), R.string.training_range_11),
    TWENTY_ONE(KarutaNo(21), R.string.training_range_21),
    THIRTY_ONE(KarutaNo(31), R.string.training_range_31),
    FORTY_ONE(KarutaNo(41), R.string.training_range_41),
    FIFTY_ONE(KarutaNo(51), R.string.training_range_51),
    SIXTY_ONE(KarutaNo(61), R.string.training_range_61),
    SEVENTY_ONE(KarutaNo(71), R.string.training_range_71),
    EIGHTY_ONE(KarutaNo(81), R.string.training_range_81),
    NINETY_ONE(KarutaNo(91), R.string.training_range_91);

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
