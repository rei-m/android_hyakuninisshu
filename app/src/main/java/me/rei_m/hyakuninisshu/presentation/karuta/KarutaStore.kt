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

package me.rei_m.hyakuninisshu.presentation.karuta

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.karuta.FetchKarutaAction
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.presentation.Store
import me.rei_m.hyakuninisshu.util.Event
import javax.inject.Inject

class KarutaStore(dispatcher: Dispatcher) : Store() {

    private val _karuta = MutableLiveData<Karuta?>()
    val karuta: LiveData<Karuta?> = _karuta

    private val _notFoundKarutaEvent = MutableLiveData<Event<Unit>>()
    val notFoundKarutaEvent = _notFoundKarutaEvent

    init {
        _karuta.value = null
        register(dispatcher.on(FetchKarutaAction::class.java).subscribe {
            if (it.isSucceeded) {
                _karuta.value = it.karuta
            } else {
                _notFoundKarutaEvent.value = Event(Unit)
            }
        })
    }

    class Factory @Inject constructor(private val dispatcher: Dispatcher) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return KarutaStore(dispatcher) as T
        }
    }
}
