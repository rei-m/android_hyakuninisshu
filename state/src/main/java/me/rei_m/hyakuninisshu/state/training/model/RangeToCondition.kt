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
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.state.R

/**
 * 練習の条件/出題範囲終了.
 */
enum class RangeToCondition(
    val value: KarutaNo,
    @param:StringRes private val resId: Int,
) : SelectableItem {
    TEN(KarutaNo(10), R.string.training_range_10),
    TWENTY(KarutaNo(20), R.string.training_range_20),
    THIRTY(KarutaNo(30), R.string.training_range_30),
    FORTY(KarutaNo(40), R.string.training_range_40),
    FIFTY(KarutaNo(50), R.string.training_range_50),
    SIXTY(KarutaNo(60), R.string.training_range_60),
    SEVENTY(KarutaNo(70), R.string.training_range_70),
    EIGHTY(KarutaNo(80), R.string.training_range_80),
    NINETY(KarutaNo(90), R.string.training_range_90),
    ONE_HUNDRED(KarutaNo(100), R.string.training_range_100),
    ;

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
