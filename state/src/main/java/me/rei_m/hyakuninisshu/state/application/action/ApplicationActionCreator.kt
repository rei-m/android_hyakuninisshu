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

package me.rei_m.hyakuninisshu.state.application.action

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationActionCreator
    @Inject
    constructor(
        private val karutaRepository: KarutaRepository,
    ) {
        /**
         * 百人一首の情報を準備してアプリの利用を開始する.
         *
         * @return StartApplicationAction
         */
        suspend fun start() =
            try {
                karutaRepository.initialize()
                StartApplicationAction.Success()
            } catch (e: Exception) {
                StartApplicationAction.Failure(e)
            }
    }
