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
package me.rei_m.hyakuninisshu.presentation.entrance

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.rei_m.hyakuninisshu.action.material.MaterialActionCreator
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MaterialListViewModel(
    private val store: EntranceStore,
    private val actionCreator: MaterialActionCreator,
    colorFilter: ColorFilter,
    private val navigator: Navigator
) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    val karutaList: LiveData<List<Karuta>> = store.karutaList

    var colorFilter = colorFilter
        set(value) {
            launch {
                actionCreator.fetch(value.value)
            }
            field = value
        }

    init {
        launch {
            actionCreator.fetch(colorFilter.value)
        }
    }

    override fun onCleared() {
        job.cancel()
        store.dispose()
        super.onCleared()
    }

    fun onClickItem(position: Int) {
        navigator.navigateToMaterialDetail(position, colorFilter)
    }

    class Factory @Inject constructor(
        private val store: EntranceStore,
        private val actionCreator: MaterialActionCreator,
        private val navigator: Navigator
    ) : ViewModelProvider.Factory {
        var colorFilter = ColorFilter.ALL

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MaterialListViewModel(
                store,
                actionCreator,
                colorFilter,
                navigator
            ) as T
        }
    }
}
