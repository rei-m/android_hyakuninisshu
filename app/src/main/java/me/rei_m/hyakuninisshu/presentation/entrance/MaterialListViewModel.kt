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

import android.arch.lifecycle.LiveData
import android.support.v4.app.FragmentActivity
import me.rei_m.hyakuninisshu.action.material.MaterialActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.presentation.ViewModelFactory
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import javax.inject.Inject

class MaterialListViewModel(
    store: EntranceStore,
    private val actionDispatcher: MaterialActionDispatcher,
    colorFilter: ColorFilter,
    private val navigator: Navigator
) {

    val karutaList: LiveData<List<Karuta>> = store.karutaList

    var colorFilter = colorFilter
        set(value) {
            actionDispatcher.fetch(value)
            field = value
        }

    init {
        actionDispatcher.fetch(colorFilter)
    }

    fun onClickItem(position: Int) {
        navigator.navigateToMaterialDetail(position, colorFilter)
    }

    class Factory @Inject constructor(
        private val actionDispatcher: MaterialActionDispatcher,
        private val storeFactory: EntranceStore.Factory,
        private val navigator: Navigator
    ) : ViewModelFactory {
        fun create(activity: FragmentActivity, colorFilter: ColorFilter): MaterialListViewModel = MaterialListViewModel(
                obtainActivityStore(activity, EntranceStore::class.java, storeFactory),
                actionDispatcher,
                colorFilter,
                navigator
        )
    }
}
