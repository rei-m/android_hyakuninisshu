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

package me.rei_m.hyakuninisshu.state.material.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.core.Event
import me.rei_m.hyakuninisshu.state.core.Store
import me.rei_m.hyakuninisshu.state.material.action.EditMaterialAction
import javax.inject.Inject

class EditMaterialStore
    @Inject
    constructor(
        dispatcher: Dispatcher,
    ) : Store() {
        private val _onCompleteEditEvent = MutableLiveData<Event<Unit>>()
        val onCompleteEditEvent: LiveData<Event<Unit>> = _onCompleteEditEvent

        private val _isFailure = MutableLiveData(false)
        val isFailure: LiveData<Boolean> = _isFailure

        init {
            register(
                dispatcher.on(EditMaterialAction::class.java).subscribe {
                    when (it) {
                        is EditMaterialAction.Success -> {
                            _isFailure.value = false
                            _onCompleteEditEvent.value = Event(Unit)
                        }
                        is EditMaterialAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
            )
        }
    }
