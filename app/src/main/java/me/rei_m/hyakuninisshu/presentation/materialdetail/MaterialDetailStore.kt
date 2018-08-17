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

package me.rei_m.hyakuninisshu.presentation.materialdetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.material.EditMaterialAction
import me.rei_m.hyakuninisshu.action.material.FetchMaterialAction
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.presentation.Store
import me.rei_m.hyakuninisshu.util.Event
import java.util.*
import javax.inject.Inject

class MaterialDetailStore(dispatcher: Dispatcher) : Store() {

    private val _karutaList = MutableLiveData<List<Karuta>>()
    val karutaList: LiveData<List<Karuta>> = _karutaList

    private val _unhandledErrorEvent = MutableLiveData<Event<Unit>>()
    val unhandledErrorEvent: LiveData<Event<Unit>> = _unhandledErrorEvent

    init {
        register(dispatcher.on(FetchMaterialAction::class.java).subscribe {
            if (it.error == null) {
                _karutaList.value = it.karutas?.asList()
            } else {
                _unhandledErrorEvent.value = Event(Unit)
            }
        }, dispatcher.on(EditMaterialAction::class.java).subscribe { action ->
            if (action.error != null) {
                _unhandledErrorEvent.value = Event(Unit)
                return@subscribe
            }
            _karutaList.value?.let {
                val karutaList = ArrayList(it)
                karutaList[karutaList.indexOf(action.karuta)] = action.karuta
                _karutaList.value = karutaList
            }
        })
    }

    class Factory @Inject constructor(private val dispatcher: Dispatcher) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MaterialDetailStore(dispatcher) as T
        }
    }
}
