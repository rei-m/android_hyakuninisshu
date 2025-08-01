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

package me.rei_m.hyakuninisshu.feature.material.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import me.rei_m.hyakuninisshu.feature.corecomponent.ui.AbstractViewModel
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.material.action.MaterialActionCreator
import me.rei_m.hyakuninisshu.state.material.model.ColorFilter
import me.rei_m.hyakuninisshu.state.material.store.MaterialStore
import javax.inject.Inject

class MaterialViewModel(
    dispatcher: Dispatcher,
    private val actionCreator: MaterialActionCreator,
    private val store: MaterialStore,
    private val handle: SavedStateHandle,
) : AbstractViewModel(dispatcher) {
    val materialList = store.materialList

    var colorFilter: ColorFilter
        get() {
            val ordinal: Int = handle.get<Int>(KEY_COLOR_FILTER) ?: ColorFilter.ALL.ordinal
            return ColorFilter[ordinal]
        }
        set(value) {
            dispatchAction { actionCreator.fetchMaterialList(value) }
            handle[KEY_COLOR_FILTER] = value.ordinal
        }

    init {
        dispatchAction { actionCreator.fetchMaterialList(colorFilter) }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory
        @Inject
        constructor(
            private val dispatcher: Dispatcher,
            private val actionCreator: MaterialActionCreator,
            private val store: MaterialStore,
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                val savedStateHandle = extras.createSavedStateHandle()
                return MaterialViewModel(dispatcher, actionCreator, store, savedStateHandle) as T
            }
        }

    companion object {
        private const val KEY_COLOR_FILTER = "colorFilter"
    }
}
