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

package me.rei_m.hyakuninisshu.action.application

import kotlinx.coroutines.experimental.launch
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

@Singleton
class ApplicationActionDispatcher @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val dispatcher: Dispatcher,
    private val coroutineContext: CoroutineContext
) {

    /**
     * 百人一首の情報を準備してアプリの利用を開始する.
     */
    fun start() {
        launch(coroutineContext) {
            try {
                karutaRepository.initialize()
                dispatcher.dispatch(StartApplicationAction())
            } catch (e: IOException) {
                dispatcher.dispatch(StartApplicationAction(e))
            }
        }
    }
}
