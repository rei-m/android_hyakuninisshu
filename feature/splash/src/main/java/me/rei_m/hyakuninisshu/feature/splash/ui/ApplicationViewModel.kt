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
package me.rei_m.hyakuninisshu.feature.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.rei_m.hyakuninisshu.action.application.ApplicationActionCreator
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class ApplicationViewModel(
    mainContext: CoroutineContext,
    ioContext: CoroutineContext,
    private val store: ApplicationStore,
    actionCreator: ApplicationActionCreator
) : AbstractViewModel(mainContext) {

    val isReady = store.isReady

    val unhandledErrorEvent = store.unhandledErrorEvent

    init {
        launch {
            withContext(ioContext) { actionCreator.start() }
        }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        @Named("mainContext") private val mainContext: CoroutineContext,
        @Named("ioContext") private val ioContext: CoroutineContext,
        private val store: ApplicationStore,
        private val actionCreator: ApplicationActionCreator
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ApplicationViewModel(mainContext, ioContext, store, actionCreator) as T
        }
    }
}
