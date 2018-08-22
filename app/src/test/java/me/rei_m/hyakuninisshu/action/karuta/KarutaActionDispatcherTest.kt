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

package me.rei_m.hyakuninisshu.action.karuta

import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.helper.DirectCoroutineContext
import me.rei_m.hyakuninisshu.helper.TestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaActionDispatcherTest : TestHelper {

    private lateinit var actionDispatcher: KarutaActionDispatcher

    private lateinit var karutaRepository: KarutaRepository

    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = mock {}
        karutaRepository = mock {}
        actionDispatcher = KarutaActionDispatcher(
            karutaRepository,
            dispatcher,
            DirectCoroutineContext
        )
    }

    @Test
    fun fetch() {
        val karuta = createKaruta(id = 1)

        whenever(karutaRepository.findBy(karuta.identifier())).thenReturn(karuta)

        actionDispatcher.fetch(karuta.identifier())

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchKarutaAction::class.java)
            if (it is FetchKarutaAction) {
                assertThat(it.karuta).isEqualTo(karuta)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchWhenNotFound() {
        whenever(karutaRepository.findBy(KarutaIdentifier(1))).thenReturn(null)

        actionDispatcher.fetch(KarutaIdentifier(1))

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchKarutaAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }
}
