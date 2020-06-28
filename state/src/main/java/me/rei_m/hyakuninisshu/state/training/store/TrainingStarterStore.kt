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
import me.rei_m.hyakuninisshu.state.core.Event
import me.rei_m.hyakuninisshu.state.core.Store
import me.rei_m.hyakuninisshu.state.training.action.StartTrainingAction
import javax.inject.Inject

/**
 * 練習の開始状態を管理する.
 */
class TrainingStarterStore @Inject constructor(dispatcher: Dispatcher) : Store() {

    private val _onReadyEvent = MutableLiveData<Event<String>>()
    val onReadyEvent: LiveData<Event<String>> = _onReadyEvent

    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _isFailure = MutableLiveData(false)
    val isFailure: LiveData<Boolean> = _isFailure

    init {
        register(dispatcher.on(StartTrainingAction::class.java).subscribe {
            when (it) {
                is StartTrainingAction.Success -> {
                    _onReadyEvent.value = Event(it.questionId)
                    _isEmpty.value = false
                    _isFailure.value = false
                }
                is StartTrainingAction.Empty -> {
                    _isEmpty.value = true
                    _isFailure.value = false
                }
                is StartTrainingAction.Failure -> {
                    _isFailure.value = true
                }
            }
        })
    }
}
