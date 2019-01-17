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
package me.rei_m.hyakuninisshu.presentation.examhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.rei_m.hyakuninisshu.action.exam.ExamActionCreator
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.ext.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ExamHistoryViewModel(
    private val store: ExamHistoryStore,
    private val actionCreator: ExamActionCreator
) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    val isLoading: LiveData<Boolean> = store.karutaExamList.map { it == null }

    val karutaExamList: LiveData<List<KarutaExam>?> = store.karutaExamList

    val unhandledErrorEvent = store.unhandledErrorEvent

    init {
        launch { actionCreator.fetchAll() }
    }

    override fun onCleared() {
        job.cancel()
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        private val store: ExamHistoryStore,
        private val actionCreator: ExamActionCreator
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamHistoryViewModel(store, actionCreator) as T
        }
    }
}
