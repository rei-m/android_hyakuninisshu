/*
 * Copyright (c) 2019. Rei Matsushita
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
package me.rei_m.hyakuninisshu.feature.materiallist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.material.MaterialActionCreator
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class MaterialListViewModel(
    mainContext: CoroutineContext,
    ioContext: CoroutineContext,
    private val store: MaterialListStore,
    private val actionCreator: MaterialActionCreator,
    dispatcher: Dispatcher,
    colorFilter: ColorFilter
) : AbstractViewModel(mainContext, ioContext, dispatcher) {

    val karutaList: LiveData<List<Karuta>> = store.karutaList

    var colorFilter = colorFilter
        set(value) {
            dispatchAction { actionCreator.fetch(value.value) }
            field = value
        }

    init {
        dispatchAction { actionCreator.fetch(colorFilter.value) }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        @Named("mainContext") private val mainContext: CoroutineContext,
        @Named("ioContext") private val ioContext: CoroutineContext,
        private val store: MaterialListStore,
        private val actionCreator: MaterialActionCreator,
        private val dispatcher: Dispatcher
    ) : ViewModelProvider.Factory {
        var colorFilter = ColorFilter.ALL

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MaterialListViewModel(
                mainContext,
                ioContext,
                store,
                actionCreator,
                dispatcher,
                colorFilter
            ) as T
        }
    }
}
