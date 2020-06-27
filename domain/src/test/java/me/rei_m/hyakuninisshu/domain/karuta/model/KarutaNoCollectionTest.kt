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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class KarutaNoCollectionTest {

    private lateinit var karutaNoCollection: KarutaNoCollection

    @Before
    fun setUp() {
        karutaNoCollection = KarutaNoCollection(
            arrayListOf(
                KarutaNo(1),
                KarutaNo(2),
                KarutaNo(3)
            )
        )
    }

    @Test
    fun createInstance() {
        val expected = arrayListOf(
            KarutaNo(1),
            KarutaNo(2),
            KarutaNo(3)
        )
        assertThat(karutaNoCollection.values).isEqualTo(expected)
        assertThat(karutaNoCollection.size).isEqualTo(3)
    }

    @Test
    fun asRandomized() {
        assertThat(karutaNoCollection.asRandomized).containsAll(karutaNoCollection.values)
    }

    @Test
    fun contains() {
        assertThat(karutaNoCollection.contains(KarutaNo(1))).isTrue()
    }

    @Test
    fun notContains() {
        assertThat(karutaNoCollection.contains(KarutaNo(4))).isFalse()
    }
}
