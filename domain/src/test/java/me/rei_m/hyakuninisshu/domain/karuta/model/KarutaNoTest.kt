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
import org.junit.Test

class KarutaNoTest {
    @Test
    fun createWith001() {
        val (value) = KarutaNo(1)
        assertThat(value).isEqualTo(1)
    }

    @Test
    fun createWith050() {
        val (value) = KarutaNo(50)
        assertThat(value).isEqualTo(50)
    }

    @Test
    fun createWith100() {
        val (value) = KarutaNo(100)
        assertThat(value).isEqualTo(100)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createWith000() {
        KarutaNo(0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createWith101() {
        KarutaNo(101)
    }
}
