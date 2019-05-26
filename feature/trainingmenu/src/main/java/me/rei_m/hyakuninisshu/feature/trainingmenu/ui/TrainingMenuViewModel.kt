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

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KimarijiFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.QuizAnimationSpeed
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.TrainingRangeFrom
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.TrainingRangeTo
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.withValue
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import javax.inject.Inject

class TrainingMenuViewModel(
    trainingRangeFrom: TrainingRangeFrom,
    trainingRangeTo: TrainingRangeTo,
    kimariji: KimarijiFilter,
    kamiNoKuStyle: KarutaStyleFilter,
    shimoNoKuStyle: KarutaStyleFilter,
    color: ColorFilter,
    animationSpeed: QuizAnimationSpeed,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel() {
    val trainingRangeFrom = MutableLiveData<TrainingRangeFrom>().withValue(trainingRangeFrom)

    val trainingRangeTo = MutableLiveData<TrainingRangeTo>().withValue(trainingRangeTo)

    val kimariji = MutableLiveData<KimarijiFilter>().withValue(kimariji)

    val kamiNoKuStyle = MutableLiveData<KarutaStyleFilter>().withValue(kamiNoKuStyle)

    val shimoNoKuStyle = MutableLiveData<KarutaStyleFilter>().withValue(shimoNoKuStyle)

    val color = MutableLiveData<ColorFilter>().withValue(color)

    val animationSpeed = MutableLiveData<QuizAnimationSpeed>().withValue(animationSpeed)

    class Factory @Inject constructor(
        private val analyticsHelper: AnalyticsHelper
    ) : ViewModelProvider.Factory {
        var trainingRangeFrom = TrainingRangeFrom.ONE
        var trainingRangeTo = TrainingRangeTo.ONE_HUNDRED
        var kimariji = KimarijiFilter.ALL
        var kamiNoKuStyle = KarutaStyleFilter.KANJI
        var shimoNoKuStyle = KarutaStyleFilter.KANA
        var color = ColorFilter.ALL
        var animationSpeed = QuizAnimationSpeed.NORMAL

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrainingMenuViewModel(
                trainingRangeFrom,
                trainingRangeTo,
                kimariji,
                kamiNoKuStyle,
                shimoNoKuStyle,
                color,
                animationSpeed,
                analyticsHelper
            ) as T
        }
    }
}