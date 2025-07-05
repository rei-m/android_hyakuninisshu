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

package me.rei_m.hyakuninisshu.state.exam.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.core.Event
import me.rei_m.hyakuninisshu.state.core.Store
import me.rei_m.hyakuninisshu.state.exam.action.FinishExamAction
import javax.inject.Inject

/**
 * 力試しの完了状態を管理する.
 */
class ExamFinisherStore
    @Inject
    constructor(
        dispatcher: Dispatcher,
    ) : Store() {
        /**
         * 力試しの完了を通知するイベント.
         */
        private val _onFinishEvent = MutableLiveData<Event<Long>>()
        val onFinishEvent: LiveData<Event<Long>> = _onFinishEvent

        private val _isFailure = MutableLiveData(false)
        val isFailure: LiveData<Boolean> = _isFailure

        init {
            register(
                dispatcher.on(FinishExamAction::class.java).subscribe {
                    when (it) {
                        is FinishExamAction.Success -> {
                            _onFinishEvent.value = Event(it.examResult.id)
                            _isFailure.value = false
                        }
                        is FinishExamAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
            )
        }
    }
