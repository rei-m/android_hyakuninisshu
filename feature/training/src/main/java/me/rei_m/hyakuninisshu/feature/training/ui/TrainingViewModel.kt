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
package me.rei_m.hyakuninisshu.feature.training.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.rei_m.hyakuninisshu.action.training.TrainingActionCreator
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KimarijiFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.TrainingRangeFrom
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.TrainingRangeTo
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class TrainingViewModel(
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val store: TrainingStore,
    private val actionCreator: TrainingActionCreator
) : AbstractViewModel(mainContext) {

    val currentKarutaQuizId: LiveData<KarutaQuizIdentifier?> = store.currentKarutaQuizId

    val isVisibleAd: LiveData<Boolean> = currentKarutaQuizId.map { it == null }

    val isVisibleEmpty: LiveData<Boolean> = store.empty

    val unhandledErrorEvent: LiveData<Event<Unit>> = store.unhandledErrorEvent

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    fun startTraining(
        trainingRangeFrom: TrainingRangeFrom,
        trainingRangeTo: TrainingRangeTo,
        kimarijiFilter: KimarijiFilter,
        colorFilter: ColorFilter
    ) {
        launch {
            withContext(ioContext) {
                actionCreator.start(
                    trainingRangeFrom.value,
                    trainingRangeTo.value,
                    kimarijiFilter.value,
                    colorFilter.value
                )
            }
        }
    }

    fun startTraining() {
        launch {
            withContext(ioContext) { actionCreator.startForExam() }
        }
    }

    fun fetchNext() {
        launch {
            actionCreator.fetchNext()
        }
    }

    class Factory @Inject constructor(
        @Named("mainContext") private val mainContext: CoroutineContext,
        @Named("ioContext") private val ioContext: CoroutineContext,
        private val store: TrainingStore,
        private val actionCreator: TrainingActionCreator
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrainingViewModel(
                mainContext,
                ioContext,
                store,
                actionCreator
            ) as T
        }
    }
}
