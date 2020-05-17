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
package me.rei_m.hyakuninisshu.feature.materialdetail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.material.MaterialActionCreator
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.withValue
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import kotlin.coroutines.CoroutineContext

class MaterialDetailViewModel(
    mainContext: CoroutineContext,
    ioContext: CoroutineContext,
    dispatcher: Dispatcher,
    actionCreator: MaterialActionCreator,
    private val store: MaterialDetailStore,
    colorFilter: ColorFilter,
    initialPosition: Int
) : AbstractViewModel(mainContext, ioContext, dispatcher) {

    val karutaList: LiveData<List<Karuta>> = store.karutaList

    val initialPosition: LiveData<Int> = MutableLiveData<Int>().withValue(initialPosition)

    val unhandledErrorEvent: LiveData<Event<Unit>> = store.unhandledErrorEvent

    init {
        dispatchAction { actionCreator.fetch(colorFilter.value) }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory(
        private val mainContext: CoroutineContext,
        private val ioContext: CoroutineContext,
        private val dispatcher: Dispatcher,
        private val actionCreator: MaterialActionCreator,
        private val store: MaterialDetailStore,
        private val colorFilter: ColorFilter,
        private val initialPosition: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MaterialDetailViewModel(
            mainContext,
            ioContext,
            dispatcher,
            actionCreator,
            store,
            colorFilter,
            initialPosition
        ) as T
    }
}
