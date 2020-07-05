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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.feature.trainingmenu.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import me.rei_m.hyakuninisshu.state.training.model.ColorCondition
import me.rei_m.hyakuninisshu.state.training.model.DisplayAnimationSpeedCondition
import me.rei_m.hyakuninisshu.state.training.model.DisplayStyleCondition
import me.rei_m.hyakuninisshu.state.training.model.InputSecondCondition
import me.rei_m.hyakuninisshu.state.training.model.KimarijiCondition
import me.rei_m.hyakuninisshu.state.training.model.RangeFromCondition
import me.rei_m.hyakuninisshu.state.training.model.RangeToCondition

class TrainingMenuViewModel(private val handle: SavedStateHandle) : ViewModel() {
    var rangeFrom: RangeFromCondition
        get() {
            val ordinal: Int = handle.get<Int>(KEY_RANGE_FROM) ?: RangeFromCondition.ONE.ordinal
            return RangeFromCondition[ordinal]
        }
        set(value) {
            handle.set(KEY_RANGE_FROM, value.ordinal)
        }

    var rangeTo: RangeToCondition
        get() {
            val ordinal: Int = handle.get<Int>(KEY_RANGE_TO) ?: RangeToCondition.ONE_HUNDRED.ordinal
            return RangeToCondition[ordinal]
        }
        set(value) {
            handle.set(KEY_RANGE_TO, value.ordinal)
        }

    var kimariji: KimarijiCondition
        get() {
            val ordinal: Int = handle.get<Int>(KEY_KIMARIJI) ?: KimarijiCondition.ALL.ordinal
            return KimarijiCondition[ordinal]
        }
        set(value) {
            handle.set(KEY_KIMARIJI, value.ordinal)
        }

    var kamiNoKuStyle: DisplayStyleCondition
        get() {
            val ordinal: Int =
                handle.get<Int>(KEY_KAMI_NO_KU_STYLE) ?: DisplayStyleCondition.KANJI.ordinal
            return DisplayStyleCondition[ordinal]
        }
        set(value) {
            handle.set(KEY_KAMI_NO_KU_STYLE, value.ordinal)
        }

    var shimoNoKuStyle: DisplayStyleCondition
        get() {
            val ordinal: Int =
                handle.get<Int>(KEY_SHIMO_NO_KU_STYLE) ?: DisplayStyleCondition.KANA.ordinal
            return DisplayStyleCondition[ordinal]
        }
        set(value) {
            handle.set(KEY_SHIMO_NO_KU_STYLE, value.ordinal)
        }

    var color: ColorCondition
        get() {
            val ordinal: Int = handle.get<Int>(KEY_COLOR) ?: ColorCondition.ALL.ordinal
            return ColorCondition[ordinal]
        }
        set(value) {
            handle.set(KEY_COLOR, value.ordinal)
        }

    var inputSecond: InputSecondCondition
        get() {
            val ordinal: Int =
                handle.get<Int>(KEY_INPUT_SPEED) ?: InputSecondCondition.NONE.ordinal
            return InputSecondCondition[ordinal]
        }
        set(value) {
            handle.set(KEY_INPUT_SPEED, value.ordinal)
        }

    var animationSpeed: DisplayAnimationSpeedCondition
        get() {
            val ordinal: Int = handle.get<Int>(KEY_ANIMATION_SPEED)
                ?: DisplayAnimationSpeedCondition.NORMAL.ordinal
            return DisplayAnimationSpeedCondition[ordinal]
        }
        set(value) {
            handle.set(KEY_ANIMATION_SPEED, value.ordinal)
        }

    companion object {
        private const val KEY_RANGE_FROM = "rangeFrom"
        private const val KEY_RANGE_TO = "rangeTo"
        private const val KEY_KIMARIJI = "kimarij"
        private const val KEY_KAMI_NO_KU_STYLE = "kamiNoKuStyle"
        private const val KEY_SHIMO_NO_KU_STYLE = "shimoNoKuStyle"
        private const val KEY_COLOR = "color"
        private const val KEY_INPUT_SPEED = "inputSpeed"
        private const val KEY_ANIMATION_SPEED = "animationSpeed"
    }
}
