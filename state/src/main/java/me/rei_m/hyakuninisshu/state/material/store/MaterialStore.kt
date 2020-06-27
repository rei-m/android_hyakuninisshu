/*
 * Copyright (c) 2020. Rei Matsushita
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

package me.rei_m.hyakuninisshu.state.material.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.core.Store
import me.rei_m.hyakuninisshu.state.material.action.EditMaterialAction
import me.rei_m.hyakuninisshu.state.material.action.FetchMaterialListAction
import me.rei_m.hyakuninisshu.state.material.model.Material
import javax.inject.Inject

/**
 * 資料の状態を管理する.
 */
class MaterialStore @Inject constructor(dispatcher: Dispatcher) : Store() {

    private val _materialList = MutableLiveData<List<Material>?>()
    val materialList: LiveData<List<Material>?> = _materialList

    private val _isFailure = MutableLiveData(false)
    val isFailure: LiveData<Boolean> = _isFailure

    init {
        register(dispatcher.on(FetchMaterialListAction::class.java).subscribe {
            when (it) {
                is FetchMaterialListAction.Success -> {
                    _materialList.value = it.materialList
                    _isFailure.value = false
                }
                is FetchMaterialListAction.Failure -> {
                    _isFailure.value = true
                }
            }
        }, dispatcher.on(EditMaterialAction::class.java).subscribe {
            when (it) {
                is EditMaterialAction.Success -> {
                    _isFailure.value = false
                    val currentValue = materialList.value ?: return@subscribe
                    _materialList.value = currentValue.map { m ->
                        if (m.no == it.material.no) it.material else m
                    }
                }
                is EditMaterialAction.Failure -> {
                    _isFailure.value = true
                }
            }
        })
    }
}
