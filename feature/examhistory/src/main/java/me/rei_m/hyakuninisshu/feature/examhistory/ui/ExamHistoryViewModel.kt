/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.feature.examhistory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import me.rei_m.hyakuninisshu.feature.corecomponent.ui.AbstractViewModel
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.exam.action.ExamActionCreator
import me.rei_m.hyakuninisshu.state.exam.store.ExamHistoryStore
import javax.inject.Inject

class ExamHistoryViewModel(
    dispatcher: Dispatcher,
    private val actionCreator: ExamActionCreator,
    private val store: ExamHistoryStore,
) : AbstractViewModel(dispatcher) {
    val resultList = store.resultList

    val isVisibleProgress = store.resultList.map { it == null }

    init {
        dispatchAction { actionCreator.fetchAllResult() }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory
        @Inject
        constructor(
            private val dispatcher: Dispatcher,
            private val actionCreator: ExamActionCreator,
            private val store: ExamHistoryStore,
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                ExamHistoryViewModel(
                    dispatcher,
                    actionCreator,
                    store,
                ) as T
        }
}
