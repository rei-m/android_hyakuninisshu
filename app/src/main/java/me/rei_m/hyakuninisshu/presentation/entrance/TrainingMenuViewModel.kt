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

package me.rei_m.hyakuninisshu.presentation.entrance

import android.arch.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.AnalyticsManager
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.ext.MutableLiveDataExt
import me.rei_m.hyakuninisshu.presentation.enums.*
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.presentation.util.SingleLiveEvent
import javax.inject.Inject

class TrainingMenuViewModel(
        trainingRangeFrom: TrainingRangeFrom,
        trainingRangeTo: TrainingRangeTo,
        kimariji: KimarijiFilter,
        kamiNoKuStyle: KarutaStyleFilter,
        shimoNoKuStyle: KarutaStyleFilter,
        color: ColorFilter,
        private val navigator: Navigator,
        private val analyticsManager: AnalyticsManager
): MutableLiveDataExt {
    val trainingRangeFrom = MutableLiveData<TrainingRangeFrom>().withValue(trainingRangeFrom)

    val trainingRangeTo = MutableLiveData<TrainingRangeTo>().withValue(trainingRangeTo)

    val kimariji = MutableLiveData<KimarijiFilter>().withValue(kimariji)

    val kamiNoKuStyle = MutableLiveData<KarutaStyleFilter>().withValue(kamiNoKuStyle)

    val shimoNoKuStyle = MutableLiveData<KarutaStyleFilter>().withValue(shimoNoKuStyle)

    val color = MutableLiveData<ColorFilter>().withValue(color)

    val snackBarMessage: SingleLiveEvent<Int> = SingleLiveEvent()

    fun onClickStartTraining() {

        if (trainingRangeFrom.value!!.ordinal > trainingRangeTo.value!!.ordinal) {
            snackBarMessage.value =  R.string.text_message_invalid_training_range
            return
        }

        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING)
        navigator.navigateToTrainingMaster(trainingRangeFrom.value!!,
                trainingRangeTo.value!!,
                kimariji.value!!,
                color.value!!,
                kamiNoKuStyle.value!!,
                shimoNoKuStyle.value!!)
    }

    class Factory @Inject constructor(private val navigator: Navigator,
                                      private val analyticsManager: AnalyticsManager) {

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
                analyticsManager
        )
    }
}
