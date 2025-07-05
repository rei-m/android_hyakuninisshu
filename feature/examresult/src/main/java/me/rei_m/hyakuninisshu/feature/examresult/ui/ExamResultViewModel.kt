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

package me.rei_m.hyakuninisshu.feature.examresult.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import me.rei_m.hyakuninisshu.feature.corecomponent.ui.AbstractViewModel
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.exam.action.ExamActionCreator
import me.rei_m.hyakuninisshu.state.exam.store.ExamResultStore
import me.rei_m.hyakuninisshu.state.material.model.Material
import javax.inject.Inject

class ExamResultViewModel(
    private val examId: Long,
    dispatcher: Dispatcher,
    actionCreator: ExamActionCreator,
    private val store: ExamResultStore,
) : AbstractViewModel(dispatcher) {
    val result = store.result

    val materialMap =
        store.materialList.map {
            val temp = hashMapOf<Int, Material>()
            it?.forEach { material -> temp[material.no] = material }
            return@map temp
        }

    init {
        dispatchAction {
            actionCreator.fetchResult(examId)
        }
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
            private val store: ExamResultStore,
        ) : ViewModelProvider.Factory {
            var examId: Long = -1

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                ExamResultViewModel(
                    examId,
                    dispatcher,
                    actionCreator,
                    store,
                ) as T
        }
}
