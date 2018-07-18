/*
 * Copyright (c) 2017. Rei Matsushita
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

package me.rei_m.hyakuninisshu.presentation.enums

import android.content.res.Resources
import android.support.annotation.StringRes
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier

enum class TrainingRangeFrom(@param:StringRes private val resId: Int, id: Int) : SpinnerItem {
    ONE(R.string.training_range_1, 1),
    ELEVEN(R.string.training_range_11, 11),
    TWENTY_ONE(R.string.training_range_21, 21),
    THIRTY_ONE(R.string.training_range_31, 31),
    FORTY_ONE(R.string.training_range_41, 41),
    FIFTY_ONE(R.string.training_range_51, 51),
    SIXTY_ONE(R.string.training_range_61, 61),
    SEVENTY_ONE(R.string.training_range_71, 71),
    EIGHTY_ONE(R.string.training_range_81, 81),
    NINETY_ONE(R.string.training_range_91, 91);

    val identifier: KarutaIdentifier = KarutaIdentifier(id)

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
