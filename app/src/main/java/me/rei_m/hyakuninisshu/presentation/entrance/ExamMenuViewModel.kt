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

import android.arch.lifecycle.LiveData
import android.support.v4.app.FragmentActivity
import me.rei_m.hyakuninisshu.action.exam.ExamActionDispatcher
import me.rei_m.hyakuninisshu.ext.map
import me.rei_m.hyakuninisshu.presentation.ViewModelFactory
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.util.AnalyticsHelper
import javax.inject.Inject

class ExamMenuViewModel(
    store: EntranceStore,
    actionDispatcher: ExamActionDispatcher,
    private val navigator: Navigator,
    private val analyticsHelper: AnalyticsHelper
) {
    val hasResult: LiveData<Boolean> = store.recentExam.map { it != null }

    val score: LiveData<String?> = store.recentExam.map { it?.result?.score }

    val averageAnswerSec: LiveData<Float?> = store.recentExam.map { it?.result?.averageAnswerSec }

    init {
        actionDispatcher.fetchRecent()
    }

    fun onClickShowAllExamResults() {
        navigator.navigateToExamHistory()
    }

    fun onClickStartExam() {
        analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_EXAM)
        navigator.navigateToExam()
    }

    fun onClickStartTraining() {
        analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_TRAINING_FOR_EXAM)
        navigator.navigateToTrainingExam()
    }

    class Factory @Inject constructor(
        private val actionDispatcher: ExamActionDispatcher,
        private val storeFactory: EntranceStore.Factory,
        private val navigator: Navigator,
        private val analyticsHelper: AnalyticsHelper
    ) : ViewModelFactory {
        fun create(activity: FragmentActivity) = ExamMenuViewModel(
            obtainActivityStore(activity, EntranceStore::class.java, storeFactory),
            actionDispatcher,
            navigator,
            analyticsHelper
        )
    }
}
