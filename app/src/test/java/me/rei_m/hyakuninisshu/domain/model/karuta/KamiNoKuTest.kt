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

class KamiNoKuTest {

    private lateinit var kamiNoKu: KamiNoKu
    private lateinit var first: Phrase
    private lateinit var second: Phrase
    private lateinit var third: Phrase

    @Before
    fun setUp() {
        val identifier = KamiNoKuIdentifier(1)
        first = Phrase("しょく", "初句")
        second = Phrase("にく", "二句")
        third = Phrase("さんく", "三句")
        kamiNoKu = KamiNoKu(identifier, first, second, third)
    }

    @Test
    fun createInstance() {
        assertThat(kamiNoKu.first).isEqualTo(first)
        assertThat(kamiNoKu.second).isEqualTo(second)
        assertThat(kamiNoKu.third).isEqualTo(third)
        assertThat(kamiNoKu.kanji).isEqualTo("初句　二句　三句")
        assertThat(kamiNoKu.kana).isEqualTo("しょく　にく　さんく")
    }
}
