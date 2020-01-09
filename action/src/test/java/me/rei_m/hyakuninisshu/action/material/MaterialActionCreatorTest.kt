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
package me.rei_m.hyakuninisshu.action.material

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class MaterialActionCreatorTest : TestHelper {

    private lateinit var actionCreator: MaterialActionCreator

    private lateinit var karutaRepository: KarutaRepository

    @Before
    fun setUp() {
        karutaRepository = mock {}
        actionCreator = MaterialActionCreator(karutaRepository)
    }

    @Test
    fun fetch() {
        val karutas = Karutas(listOf(createKaruta(id = 1)))

        whenever(karutaRepository.list(Color.BLUE)).thenReturn(karutas)

        val actual = actionCreator.fetch(Color.BLUE)

        assertThat(actual.karutas).isEqualTo(karutas)
        assertThat(actual.error).isNull()
    }

    @Test
    fun startEdit() {
        val karuta = createKaruta(id = 1)

        whenever(karutaRepository.findBy(karuta.identifier)).thenReturn(karuta)

        val actual = actionCreator.startEdit(karuta.identifier)

        assertThat(actual.karuta).isEqualTo(karuta)
        assertThat(actual.error).isNull()
    }

    @Test
    fun startEditWhenNotFound() {
        whenever(karutaRepository.findBy(KarutaIdentifier(1))).thenReturn(null)

        val actual = actionCreator.startEdit(KarutaIdentifier(1))

        assertThat(actual).isInstanceOf(StartEditMaterialAction::class.java)
        assertThat(actual.error).isNotNull()
    }

    @Test
    fun edit() {
        val karuta = createKaruta(1)

        whenever(karutaRepository.findBy(karuta.identifier)).thenReturn(karuta)
        whenever(karutaRepository.store(any())).thenAnswer { }

        val actual = actionCreator.edit(
            karuta.identifier,
            "初句改",
            "しょくかい",
            "二句改",
            "にくかい",
            "三句改",
            "さんくかい",
            "四句改",
            "よんくかい",
            "五句改",
            "ごくかい"
        )

        assertThat(actual.karuta).isEqualTo(karuta)
        assertThat(actual.karuta!!.kamiNoKu.first.kanji).isEqualTo("初句改")
        assertThat(actual.karuta!!.kamiNoKu.first.kana).isEqualTo("しょくかい")
        assertThat(actual.karuta!!.kamiNoKu.second.kanji).isEqualTo("二句改")
        assertThat(actual.karuta!!.kamiNoKu.second.kana).isEqualTo("にくかい")
        assertThat(actual.karuta!!.kamiNoKu.third.kanji).isEqualTo("三句改")
        assertThat(actual.karuta!!.kamiNoKu.third.kana).isEqualTo("さんくかい")
        assertThat(actual.karuta!!.shimoNoKu.fourth.kanji).isEqualTo("四句改")
        assertThat(actual.karuta!!.shimoNoKu.fourth.kana).isEqualTo("よんくかい")
        assertThat(actual.karuta!!.shimoNoKu.fifth.kanji).isEqualTo("五句改")
        assertThat(actual.karuta!!.shimoNoKu.fifth.kana).isEqualTo("ごくかい")
        assertThat(actual.error).isNull()
    }

    @Test
    fun editWhenNotFound() {
        val karuta = createKaruta(1)

        whenever(karutaRepository.findBy(karuta.identifier)).thenReturn(null)

        val actual = actionCreator.edit(
            karuta.identifier,
            "初句改",
            "しょくかい",
            "二句改",
            "にくかい",
            "三句改",
            "さんくかい",
            "四句改",
            "よんくかい",
            "五句改",
            "ごくかい"
        )

        assertThat(actual).isInstanceOf(EditMaterialAction::class.java)
        assertThat(actual.error).isNotNull()
    }
}
