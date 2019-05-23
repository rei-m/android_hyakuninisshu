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
package me.rei_m.hyakuninisshu.action.karuta

import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KarutaActionCreator @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val dispatcher: Dispatcher
) {
    /**
     * 指定の詩を取り出す.
     *
     * @param karutaId 歌ID.
     */
    fun fetch(karutaId: KarutaIdentifier) {
        try {
            val karuta = karutaRepository.findBy(karutaId)
                ?: throw NoSuchElementException(karutaId.toString())
            dispatcher.dispatch(FetchKarutaAction.createSuccess(karuta))
        } catch (e: Exception) {
            dispatcher.dispatch(FetchKarutaAction.createError(e))
        }
    }
}