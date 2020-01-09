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

package me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.rei_m.hyakuninisshu.action.Action
import me.rei_m.hyakuninisshu.action.Dispatcher
import kotlin.coroutines.CoroutineContext

// mainContextはktx使えばいらないやつ（気が向いたら置き換える）
abstract class AbstractViewModel(
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val dispatcher: Dispatcher
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = mainContext + job

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    fun dispatchAction(block: suspend CoroutineScope.() -> Action) {
        launch {
            val action = withContext(ioContext, block)
            dispatcher.dispatch(action)
        }
    }
}
