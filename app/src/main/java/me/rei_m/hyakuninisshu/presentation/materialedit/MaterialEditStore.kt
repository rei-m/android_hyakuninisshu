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

package me.rei_m.hyakuninisshu.presentation.materialedit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.material.EditMaterialAction
import me.rei_m.hyakuninisshu.action.material.StartEditMaterialAction
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.presentation.Store
import me.rei_m.hyakuninisshu.presentation.helper.SingleLiveEvent
import javax.inject.Inject

class MaterialEditStore(dispatcher: Dispatcher) : Store() {

    private val _karuta = MutableLiveData<Karuta?>()
    val karuta: LiveData<Karuta?> = _karuta

    private val _completeEditEvent = SingleLiveEvent<Void>()
    val completeEditEvent: LiveData<Void> = _completeEditEvent

    private val _unhandledErrorEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val unhandledErrorEvent: LiveData<Void> = _unhandledErrorEvent

    init {
        register(dispatcher.on(StartEditMaterialAction::class.java).subscribe {
            if (it.error == null) {
                _karuta.value = it.karuta
            } else {
                _unhandledErrorEvent.call()
            }
        }, dispatcher.on(EditMaterialAction::class.java).subscribe {
            if (it.error == null) {
                _karuta.value = null
                _completeEditEvent.call()
            } else {
                _unhandledErrorEvent.call()
            }
        })
    }

    class Factory @Inject constructor(private val dispatcher: Dispatcher) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MaterialEditStore(dispatcher) as T
        }
    }
}
