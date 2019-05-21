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

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.rei_m.hyakuninisshu.action.exam.ExamActionCreator
import me.rei_m.hyakuninisshu.ext.map
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.util.AnalyticsHelper
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ExamMenuViewModel(
    private val store: EntranceStore,
    actionCreator: ExamActionCreator,
    private val navigator: Navigator,
    private val analyticsHelper: AnalyticsHelper
) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    val hasResult: LiveData<Boolean> = store.recentExam.map { it != null }

    val score: LiveData<String?> = store.recentExam.map { it?.result?.score }

    val averageAnswerSec: LiveData<Float?> = store.recentExam.map { it?.result?.averageAnswerSec }

    init {
        launch {
            actionCreator.fetchRecent()
        }
    }

    override fun onCleared() {
        job.cancel()
        store.dispose()
        super.onCleared()
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
        private val store: EntranceStore,
        private val actionCreator: ExamActionCreator,
        private val navigator: Navigator,
        private val analyticsHelper: AnalyticsHelper
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamMenuViewModel(
                store,
                actionCreator,
                navigator,
                analyticsHelper
            ) as T
        }
    }
}
