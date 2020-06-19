/*
 * Copyright (c) 2020. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package me.rei_m.hyakuninisshu.feature.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.feature.corecomponent.ui.AbstractViewModel
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.application.action.ApplicationActionCreator
import me.rei_m.hyakuninisshu.state.application.store.ApplicationStore
import javax.inject.Inject

class SplashViewModel(
    dispatcher: Dispatcher,
    actionCreator: ApplicationActionCreator,
    private val store: ApplicationStore
) : AbstractViewModel(dispatcher) {

    val onReadyEvent = store.onReadyEvent

    val isFailure = store.isFailure

    init {
        dispatchAction { actionCreator.start() }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        private val dispatcher: Dispatcher,
        private val actionCreator: ApplicationActionCreator,
        private val store: ApplicationStore
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SplashViewModel(
            dispatcher,
            actionCreator,
            store
        ) as T
    }
}
