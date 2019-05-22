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
package me.rei_m.hyakuninisshu.presentation.materialdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.rei_m.hyakuninisshu.action.material.MaterialActionCreator
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.ext.withValue
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.feature.corecomponent.event.Event
import kotlin.coroutines.CoroutineContext

class MaterialDetailViewModel(
    private val store: MaterialDetailStore,
    actionCreator: MaterialActionCreator,
    colorFilter: ColorFilter,
    initialPosition: Int,
    private val navigator: Navigator
) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    val karutaList: LiveData<List<Karuta>> = store.karutaList

    val initialPosition: LiveData<Int> = MutableLiveData<Int>().withValue(initialPosition)

    val unhandledErrorEvent: LiveData<Event<Unit>> = store.unhandledErrorEvent

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

    fun onClickEdit(currentPosition: Int) {
        karutaList.value?.let {
            navigator.navigateToMaterialEdit(it[currentPosition].identifier)
        }
    }

    class Factory(
        private val store: MaterialDetailStore,
        private val actionCreator: MaterialActionCreator,
        private val navigator: Navigator,
        private val colorFilter: ColorFilter,
        private val initialPosition: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MaterialDetailViewModel(store, actionCreator, colorFilter, initialPosition, navigator) as T
        }
    }
}
