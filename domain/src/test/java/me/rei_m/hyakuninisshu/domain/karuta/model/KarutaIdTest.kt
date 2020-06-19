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

package me.rei_m.hyakuninisshu.domain.karuta.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaIdTest {

    private lateinit var karutaId: KarutaId

    @Before
    fun setUp() {
        karutaId = KarutaId(1)
    }

    @Test
    fun createInstance() {
        assertThat(karutaId.value).isEqualTo(1)
    }

    @Test
    fun equals() {
        assertThat(karutaId == KarutaId(1)).isTrue()
    }

    @Test
    fun notEquals() {
        assertThat(karutaId == KarutaId(2)).isFalse()
    }
}
