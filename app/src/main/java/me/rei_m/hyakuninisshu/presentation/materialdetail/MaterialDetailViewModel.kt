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
import me.rei_m.hyakuninisshu.action.material.MaterialActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.ext.MutableLiveDataExt
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import javax.inject.Inject

class MaterialDetailViewModel(
        store: MaterialDetailStore,
        actionDispatcher: MaterialActionDispatcher,
        colorFilter: ColorFilter,
        initialPosition: Int,
        private val navigator: Navigator
) : MutableLiveDataExt {

    val karutaList: LiveData<List<Karuta>> = store.karutaList

    val initialPosition: LiveData<Int> = MutableLiveData<Int>().withValue(initialPosition)

    init {
        actionDispatcher.fetch(colorFilter)
    }

    fun onClickEdit(currentPosition: Int) {
        karutaList.value?.let {
            navigator.navigateToMaterialEdit(it[currentPosition].identifier())
        }
    }

    class Factory @Inject constructor(private val actionDispatcher: MaterialActionDispatcher,
                                      private val navigator: Navigator) {
        fun create(store: MaterialDetailStore, colorFilter: ColorFilter, initialPosition: Int): MaterialDetailViewModel = MaterialDetailViewModel(
                store,
                actionDispatcher,
                colorFilter,
                initialPosition,
                navigator
        )
    }
}
