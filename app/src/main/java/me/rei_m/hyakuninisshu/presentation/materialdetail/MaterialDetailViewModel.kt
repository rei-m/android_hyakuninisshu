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

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.v4.app.FragmentActivity
import me.rei_m.hyakuninisshu.action.material.MaterialActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.ext.withValue
import me.rei_m.hyakuninisshu.presentation.ViewModelFactory
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.util.Event
import javax.inject.Inject

class MaterialDetailViewModel(
    store: MaterialDetailStore,
    actionDispatcher: MaterialActionDispatcher,
    colorFilter: ColorFilter,
    initialPosition: Int,
    private val navigator: Navigator
) {

    val karutaList: LiveData<List<Karuta>> = store.karutaList

    val initialPosition: LiveData<Int> = MutableLiveData<Int>().withValue(initialPosition)

    val unhandledErrorEvent: LiveData<Event<Unit>> = store.unhandledErrorEvent

    init {
        actionDispatcher.fetch(colorFilter)
    }

    fun onClickEdit(currentPosition: Int) {
        karutaList.value?.let {
            navigator.navigateToMaterialEdit(it[currentPosition].identifier)
        }
    }

    class Factory @Inject constructor(
        private val actionDispatcher: MaterialActionDispatcher,
        private val storeFactory: MaterialDetailStore.Factory,
        private val navigator: Navigator
    ) : ViewModelFactory {
        fun create(
            activity: FragmentActivity,
            colorFilter: ColorFilter,
            initialPosition: Int
        ) = MaterialDetailViewModel(
            obtainActivityStore(activity, MaterialDetailStore::class.java, storeFactory),
            actionDispatcher,
            colorFilter,
            initialPosition,
            navigator
        )
    }
}
