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

package me.rei_m.hyakuninisshu.presentation.training

import android.arch.lifecycle.LiveData
import me.rei_m.hyakuninisshu.AnalyticsManager
import me.rei_m.hyakuninisshu.action.training.TrainingActionDispatcher
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import javax.inject.Inject

class TrainingResultViewModel(store: TrainingStore,
                              private val actionDispatcher: TrainingActionDispatcher,
                              private val navigator: Navigator,
                              private val analyticsManager: AnalyticsManager) : LiveDataExt {

    val score: LiveData<String> = store.result.map { "${it.correctCount}/${it.quizCount}" }

    val averageAnswerSec: LiveData<Float> = store.result.map { it.averageAnswerSec }

    val canRestartTraining: LiveData<Boolean> = store.result.map { it.canRestartTraining }

    init {
        actionDispatcher.aggregateResults()
    }

    fun onClickPracticeWrongKarutas() {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.RESTART_TRAINING)
        actionDispatcher.restartForPractice()
    }

    fun onClickBackMenu() {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.FINISH_TRAINING)
        navigator.back()
    }

    class Factory @Inject constructor(private val actionDispatcher: TrainingActionDispatcher,
                                      private val navigator: Navigator,
                                      private val analyticsManager: AnalyticsManager) {
        fun create(store: TrainingStore): TrainingResultViewModel = TrainingResultViewModel(
                store,
                actionDispatcher,
                navigator,
                analyticsManager
        )
    }
}