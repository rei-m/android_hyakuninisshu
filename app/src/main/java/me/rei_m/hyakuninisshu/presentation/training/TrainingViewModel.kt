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

package me.rei_m.hyakuninisshu.presentation.training

import android.arch.lifecycle.LiveData
import me.rei_m.hyakuninisshu.action.training.TrainingActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.enums.KimarijiFilter
import me.rei_m.hyakuninisshu.presentation.enums.TrainingRangeFrom
import me.rei_m.hyakuninisshu.presentation.enums.TrainingRangeTo
import javax.inject.Inject

class TrainingViewModel(
        store: TrainingStore,
        private val trainingActionDispatcher: TrainingActionDispatcher
) : LiveDataExt {

    val currentKarutaQuizId: LiveData<KarutaQuizIdentifier?> = store.currentKarutaQuizId

    val isVisibleAd: LiveData<Boolean> = currentKarutaQuizId.map { it == null }

    val isVisibleEmpty: LiveData<Boolean> = store.notFoundErrorEvent

    fun startTraining(trainingRangeFrom: TrainingRangeFrom,
                      trainingRangeTo: TrainingRangeTo,
                      kimarijiFilter: KimarijiFilter,
                      colorFilter: ColorFilter) {
        trainingActionDispatcher.start(trainingRangeFrom.value,
                trainingRangeTo.value,
                kimarijiFilter.value,
                colorFilter.value)
    }

    fun startTraining() {
        trainingActionDispatcher.startForExam()
    }

    fun fetchNext() {
        trainingActionDispatcher.fetchNext()
    }

    class Factory @Inject constructor(private val actionDispatcher: TrainingActionDispatcher) {
        fun create(store: TrainingStore): TrainingViewModel = TrainingViewModel(
                store,
                actionDispatcher
        )
    }
}
