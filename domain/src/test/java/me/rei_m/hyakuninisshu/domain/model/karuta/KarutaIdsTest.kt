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
package me.rei_m.hyakuninisshu.domain.model.karuta

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class KarutaIdsTest {

    private lateinit var karutaIds: KarutaIds

    @Before
    fun setUp() {
        karutaIds = KarutaIds(arrayListOf(
            KarutaIdentifier(1),
            KarutaIdentifier(2),
            KarutaIdentifier(3)
        ))
    }

    @Test
    fun createInstance() {
        val expected = arrayListOf(
            KarutaIdentifier(1),
            KarutaIdentifier(2),
            KarutaIdentifier(3)
        )
        assertThat(karutaIds.values).isEqualTo(expected)
        assertThat(karutaIds.size).isEqualTo(3)
    }

    @Test
    fun asRandomized() {
        assertThat(karutaIds.asRandomized).containsAll(karutaIds.values)
    }

    @Test
    fun contains() {
        assertThat(karutaIds.contains(KarutaIdentifier(1))).isTrue()
    }

    @Test
    fun notContains() {
        assertThat(karutaIds.contains(KarutaIdentifier(4))).isFalse()
    }
}
