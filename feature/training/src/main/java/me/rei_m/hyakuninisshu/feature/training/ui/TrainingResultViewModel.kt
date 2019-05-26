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
import kotlinx.coroutines.launch
import me.rei_m.hyakuninisshu.action.training.TrainingActionCreator
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class TrainingResultViewModel(
    coroutineContext: CoroutineContext,
    private val store: TrainingStore,
    private val actionCreator: TrainingActionCreator,
    private val analyticsHelper: AnalyticsHelper
) : AbstractViewModel(coroutineContext) {

    val score: LiveData<String> = store.result.map { "${it.correctCount}/${it.quizCount}" }

    val averageAnswerSec: LiveData<Float> = store.result.map { it.averageAnswerSec }

    val canRestartTraining: LiveData<Boolean> = store.result.map { it.canRestartTraining }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    fun onCreateView() {
        launch {
            actionCreator.aggregateResults()
        }
    }

    fun onClickPracticeWrongKarutas() {
        analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.RESTART_TRAINING)
        launch {
            actionCreator.restartForPractice()
        }
    }

    fun onClickBackMenu() {
//        analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.FINISH_TRAINING)
//        navigator.back()
    }

    class Factory @Inject constructor(
        @Named("vmCoroutineContext") private val coroutineContext: CoroutineContext,
        private val store: TrainingStore,
        private val actionCreator: TrainingActionCreator,
        private val analyticsHelper: AnalyticsHelper
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrainingResultViewModel(
                coroutineContext,
                store,
                actionCreator,
                analyticsHelper
            ) as T
        }
    }
}
