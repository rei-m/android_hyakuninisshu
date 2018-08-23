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

class ShimoNoKuTest {

    private lateinit var shimoNoKu: ShimoNoKu
    private lateinit var fourth: Phrase
    private lateinit var fifth: Phrase

    @Before
    fun setUp() {
        val identifier = ShimoNoKuIdentifier(1)
        fourth = Phrase("よんく", "四句")
        fifth = Phrase("ごく", "五句")
        shimoNoKu = ShimoNoKu(identifier, fourth, fifth)
    }

    @Test
    fun createInstance() {
        assertThat(shimoNoKu.fourth).isEqualTo(fourth)
        assertThat(shimoNoKu.fifth).isEqualTo(fifth)
    }
}
