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
package me.rei_m.hyakuninisshu.presentation.enums

import android.content.res.Resources
import android.support.annotation.StringRes

import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier

enum class TrainingRangeTo(
    val value: KarutaIdentifier,
    @param:StringRes private val resId: Int
) : SpinnerItem {
    TEN(KarutaIdentifier(10), R.string.training_range_10),
    TWENTY(KarutaIdentifier(20), R.string.training_range_20),
    THIRTY(KarutaIdentifier(30), R.string.training_range_30),
    FORTY(KarutaIdentifier(40), R.string.training_range_40),
    FIFTY(KarutaIdentifier(50), R.string.training_range_50),
    SIXTY(KarutaIdentifier(60), R.string.training_range_60),
    SEVENTY(KarutaIdentifier(70), R.string.training_range_70),
    EIGHTY(KarutaIdentifier(80), R.string.training_range_80),
    NINETY(KarutaIdentifier(90), R.string.training_range_90),
    ONE_HUNDRED(KarutaIdentifier(100), R.string.training_range_100);

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
