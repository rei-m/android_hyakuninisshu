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

package me.rei_m.hyakuninisshu.domain.model.quiz

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KarutaQuizCounterTest {
    @Test
    fun createInstance() {
        val counter = KarutaQuizCounter(100, 1)
        assertThat(counter.value).isEqualTo("1 / 100")
        assertThat(counter.existNext).isTrue()
    }

    @Test
    fun createAllStartedInstance() {
        val counter = KarutaQuizCounter(100, 100)
        assertThat(counter.value).isEqualTo("100 / 100")
        assertThat(counter.existNext).isFalse()
    }
}
