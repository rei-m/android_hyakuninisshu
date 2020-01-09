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
package me.rei_m.hyakuninisshu.feature.exammenu.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.exam.ExamActionCreator
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class ExamMenuViewModel(
    mainContext: CoroutineContext,
    ioContext: CoroutineContext,
    private val store: ExamMenuStore,
    actionCreator: ExamActionCreator,
    dispatcher: Dispatcher
) : AbstractViewModel(mainContext, ioContext, dispatcher) {

    val hasResult: LiveData<Boolean> = store.recentExam.map { it != null }

    val score: LiveData<String?> = store.recentExam.map { it?.result?.score }

    val averageAnswerSec: LiveData<Float?> = store.recentExam.map { it?.result?.averageAnswerSec }

    init {
        dispatchAction { actionCreator.fetchRecent() }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        @Named("mainContext") private val mainContext: CoroutineContext,
        @Named("ioContext") private val ioContext: CoroutineContext,
        private val store: ExamMenuStore,
        private val actionCreator: ExamActionCreator,
        private val dispatcher: Dispatcher
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamMenuViewModel(
                mainContext,
                ioContext,
                store,
                actionCreator,
                dispatcher
            ) as T
        }
    }
}
