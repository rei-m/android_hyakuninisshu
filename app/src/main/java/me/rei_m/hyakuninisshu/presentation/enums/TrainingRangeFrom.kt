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
import androidx.annotation.StringRes
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier

enum class TrainingRangeFrom(
    val value: KarutaIdentifier,
    @param:StringRes private val resId: Int
) : SpinnerItem {
    ONE(KarutaIdentifier(1), R.string.training_range_1),
    ELEVEN(KarutaIdentifier(11), R.string.training_range_11),
    TWENTY_ONE(KarutaIdentifier(21), R.string.training_range_21),
    THIRTY_ONE(KarutaIdentifier(31), R.string.training_range_31),
    FORTY_ONE(KarutaIdentifier(41), R.string.training_range_41),
    FIFTY_ONE(KarutaIdentifier(51), R.string.training_range_51),
    SIXTY_ONE(KarutaIdentifier(61), R.string.training_range_61),
    SEVENTY_ONE(KarutaIdentifier(71), R.string.training_range_71),
    EIGHTY_ONE(KarutaIdentifier(81), R.string.training_range_81),
    NINETY_ONE(KarutaIdentifier(91), R.string.training_range_91);

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
