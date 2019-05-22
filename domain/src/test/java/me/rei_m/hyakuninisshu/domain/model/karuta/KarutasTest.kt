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

import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class KarutasTest : TestHelper {

    private lateinit var karutas: Karutas

    private lateinit var karutasValue: List<Karuta>

    @Before
    fun setUp() {
        karutasValue = arrayListOf(
            createKaruta(id = 1, color = Color.BLUE),
            createKaruta(id = 2, color = Color.BLUE),
            createKaruta(id = 3, color = Color.PINK),
            createKaruta(id = 4, color = Color.PINK)
        )
        karutas = Karutas(karutasValue)
    }

    @Test
    fun asList() {
        assertThat(karutas.asList()).isEqualTo(karutasValue)
    }

    @Test
    fun asListWithColor() {
        assertThat(karutas.asList(Color.BLUE)).isEqualTo(karutasValue.subList(0, 2))
    }

    @Test
    fun createQuizSet() {
        val karutaIds = KarutaIds(arrayListOf(KarutaIdentifier(1)))
        val actual = karutas.createQuizSet(karutaIds)
        assertThat(actual.values.size).isEqualTo(1)
        assertThat(actual.values.first().correctId).isEqualTo(karutaIds.values.first())
    }
}