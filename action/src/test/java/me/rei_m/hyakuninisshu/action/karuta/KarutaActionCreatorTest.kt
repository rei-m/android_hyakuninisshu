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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.action.karuta

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaActionCreatorTest : TestHelper {

    private lateinit var actionCreator: KarutaActionCreator

    private lateinit var karutaRepository: KarutaRepository

    @Before
    fun setUp() {
        karutaRepository = mock {}
        actionCreator = KarutaActionCreator(karutaRepository)
    }

    @Test
    fun fetch() {
        val karuta = createKaruta(id = 1)

        whenever(karutaRepository.findBy(karuta.identifier)).thenReturn(karuta)

        val actual = actionCreator.fetch(karuta.identifier)

        assertThat(actual).isInstanceOf(FetchKarutaAction::class.java)
        assertThat(actual.karuta).isEqualTo(karuta)
        assertThat(actual.error).isNull()
    }

    @Test
    fun fetchWhenNotFound() {
        whenever(karutaRepository.findBy(KarutaIdentifier(1))).thenReturn(null)

        val actual = actionCreator.fetch(KarutaIdentifier(1))
        assertThat(actual.error).isNotNull()
    }
}
