/*
 * Copyright (c) 2020. Rei Matsushita
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

package me.rei_m.hyakuninisshu.state.training.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.core.Store
import me.rei_m.hyakuninisshu.state.training.action.AggregateResultsAction
import me.rei_m.hyakuninisshu.state.training.model.TrainingResult
import javax.inject.Inject

/**
 * 練習結果の状態を管理する
 */
class TrainingResultStore @Inject constructor(dispatcher: Dispatcher) : Store() {

    private val _result = MutableLiveData<TrainingResult>()
    val result: LiveData<TrainingResult> = _result

    private val _isFailure = MutableLiveData(false)
    val isFailure: LiveData<Boolean> = _isFailure

    init {
        register(dispatcher.on(AggregateResultsAction::class.java).subscribe {
            when (it) {
                is AggregateResultsAction.Success -> {
                    _result.value = it.trainingResult
                    _isFailure.value = false
                }
                is AggregateResultsAction.Failure -> {
                    _isFailure.value = true
                }
            }
        })
    }
}
