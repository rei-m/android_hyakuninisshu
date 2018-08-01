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
import org.junit.Before
import org.junit.Test

class YomiFudaTest {

    private lateinit var yomiFuda: YomiFuda

    @Before
    fun setUp() {
        yomiFuda = YomiFuda("しょく", "にく", "さんく")
    }

    @Test
    fun createInstance() {
        assertThat(yomiFuda.firstPhrase).isEqualTo("しょく")
        assertThat(yomiFuda.secondPhrase).isEqualTo("にく")
        assertThat(yomiFuda.thirdPhrase).isEqualTo("さんく")
    }
}