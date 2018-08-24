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
package me.rei_m.hyakuninisshu.presentation.entrance

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.ext.withValue
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.presentation.enums.KimarijiFilter
import me.rei_m.hyakuninisshu.presentation.enums.TrainingRangeFrom
import me.rei_m.hyakuninisshu.presentation.enums.TrainingRangeTo
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.util.AnalyticsHelper
import me.rei_m.hyakuninisshu.util.Event
import javax.inject.Inject

class TrainingMenuViewModel(
    trainingRangeFrom: TrainingRangeFrom,
    trainingRangeTo: TrainingRangeTo,
    kimariji: KimarijiFilter,
    kamiNoKuStyle: KarutaStyleFilter,
    shimoNoKuStyle: KarutaStyleFilter,
    color: ColorFilter,
    private val navigator: Navigator,
    private val analyticsHelper: AnalyticsHelper
) {
    val trainingRangeFrom = MutableLiveData<TrainingRangeFrom>().withValue(trainingRangeFrom)

    val trainingRangeTo = MutableLiveData<TrainingRangeTo>().withValue(trainingRangeTo)

    val kimariji = MutableLiveData<KimarijiFilter>().withValue(kimariji)

    val kamiNoKuStyle = MutableLiveData<KarutaStyleFilter>().withValue(kamiNoKuStyle)

    val shimoNoKuStyle = MutableLiveData<KarutaStyleFilter>().withValue(shimoNoKuStyle)

    val color = MutableLiveData<ColorFilter>().withValue(color)

    private val _snackBarMessage = MutableLiveData<Event<Int>>()
    val snackBarMessage: LiveData<Event<Int>> = _snackBarMessage

    fun onClickStartTraining() {

        if (trainingRangeFrom.value!!.ordinal > trainingRangeTo.value!!.ordinal) {
            _snackBarMessage.value = Event(R.string.text_message_invalid_training_range)
            return
        }

        analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_TRAINING)
        navigator.navigateToTraining(
            trainingRangeFrom.value!!,
            trainingRangeTo.value!!,
            kimariji.value!!,
            color.value!!,
            kamiNoKuStyle.value!!,
            shimoNoKuStyle.value!!
        )
    }

    class Factory @Inject constructor(
        private val navigator: Navigator,
        private val analyticsHelper: AnalyticsHelper
    ) {

        var trainingRangeFrom = TrainingRangeFrom.ONE
        var trainingRangeTo = TrainingRangeTo.ONE_HUNDRED
        var kimariji = KimarijiFilter.ALL
        var kamiNoKuStyle = KarutaStyleFilter.KANJI
        var shimoNoKuStyle = KarutaStyleFilter.KANA
        var color = ColorFilter.ALL

        fun create(): TrainingMenuViewModel = TrainingMenuViewModel(
            trainingRangeFrom,
            trainingRangeTo,
            kimariji,
            kamiNoKuStyle,
            shimoNoKuStyle,
            color,
            navigator,
            analyticsHelper
        )
    }
}
