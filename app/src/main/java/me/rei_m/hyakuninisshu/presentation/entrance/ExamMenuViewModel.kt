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

package me.rei_m.hyakuninisshu.presentation.entrance

import android.arch.lifecycle.LiveData
import android.view.View
import me.rei_m.hyakuninisshu.AnalyticsManager
import me.rei_m.hyakuninisshu.action.exam.ExamActionDispatcher
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import javax.inject.Inject

class ExamMenuViewModel(
        store: EntranceStore,
        private val actionDispatcher: ExamActionDispatcher,
        private val navigator: Navigator,
        private val analyticsManager: AnalyticsManager
) : LiveDataExt {
    val hasResult: LiveData<Boolean> = store.recentExam.map { it != null }

    val score: LiveData<String?> = store.recentExam.map { it?.result?.score }

    val averageAnswerSec: LiveData<Float?> = store.recentExam.map { it?.result?.averageAnswerSec }

    fun start() {
        if (hasResult.value != true) {
            actionDispatcher.fetchRecent()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickStartExam(view: View) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_EXAM)
        navigator.navigateToExamMaster()
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClickStartTraining(view: View) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING_FOR_EXAM)
        navigator.navigateToExamTrainingMaster()
    }

    class Factory @Inject constructor(private val actionDispatcher: ExamActionDispatcher,
                                      private val navigator: Navigator,
                                      private val analyticsManager: AnalyticsManager) {
        fun create(store: EntranceStore): ExamMenuViewModel = ExamMenuViewModel(
                store,
                actionDispatcher,
                navigator,
                analyticsManager
        )
    }
}
