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

package me.rei_m.hyakuninisshu.feature.trainingstarter.ui

import androidx.lifecycle.map
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.combineLatest
import me.rei_m.hyakuninisshu.feature.corecomponent.ui.AbstractViewModel
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.training.store.TrainingStarterStore

abstract class BaseTrainingStarterVIewModel(
    private val store: TrainingStarterStore,
    dispatcher: Dispatcher,
) : AbstractViewModel(dispatcher) {
    val onReadyEvent = store.onReadyEvent

    val isEmpty = store.isEmpty

    val isFailure = store.isFailure

    val isVisibleProgress =
        isEmpty.combineLatest(isFailure).map { (_isEmpty, _isFailure) ->
            return@map !_isEmpty && !_isFailure
        }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }
}
