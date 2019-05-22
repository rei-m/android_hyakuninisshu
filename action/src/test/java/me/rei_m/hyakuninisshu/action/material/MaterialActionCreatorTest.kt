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
import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
//import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class MaterialActionCreatorTest : TestHelper {

    private lateinit var actionCreator: MaterialActionCreator

    private lateinit var karutaRepository: KarutaRepository

    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = mock {}
        karutaRepository = mock {}
        actionCreator = MaterialActionCreator(
            karutaRepository,
            dispatcher
        )
    }

    @Test
    fun fetch() {
        val karutas = Karutas(listOf(createKaruta(id = 1)))

        whenever(karutaRepository.list(Color.BLUE)).thenReturn(karutas)

        actionCreator.fetch(Color.BLUE)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchMaterialAction::class.java)
            if (it is FetchMaterialAction) {
                assertThat(it.karutas).isEqualTo(karutas)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun startEdit() {
        val karuta = createKaruta(id = 1)

        whenever(karutaRepository.findBy(karuta.identifier)).thenReturn(karuta)

        actionCreator.startEdit(karuta.identifier)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartEditMaterialAction::class.java)
            if (it is StartEditMaterialAction) {
                assertThat(it.karuta).isEqualTo(karuta)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun startEditWhenNotFound() {
        whenever(karutaRepository.findBy(KarutaIdentifier(1))).thenReturn(null)

        actionCreator.startEdit(KarutaIdentifier(1))

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartEditMaterialAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun edit() {
        val karuta = createKaruta(1)

        whenever(karutaRepository.findBy(karuta.identifier)).thenReturn(karuta)
        whenever(karutaRepository.store(any())).thenAnswer { }

        actionCreator.edit(
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

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(EditMaterialAction::class.java)
            if (it is EditMaterialAction) {
                assertThat(it.karuta).isEqualTo(karuta)
                assertThat(it.karuta!!.kamiNoKu.first.kanji).isEqualTo("初句改")
                assertThat(it.karuta!!.kamiNoKu.first.kana).isEqualTo("しょくかい")
                assertThat(it.karuta!!.kamiNoKu.second.kanji).isEqualTo("二句改")
                assertThat(it.karuta!!.kamiNoKu.second.kana).isEqualTo("にくかい")
                assertThat(it.karuta!!.kamiNoKu.third.kanji).isEqualTo("三句改")
                assertThat(it.karuta!!.kamiNoKu.third.kana).isEqualTo("さんくかい")
                assertThat(it.karuta!!.shimoNoKu.fourth.kanji).isEqualTo("四句改")
                assertThat(it.karuta!!.shimoNoKu.fourth.kana).isEqualTo("よんくかい")
                assertThat(it.karuta!!.shimoNoKu.fifth.kanji).isEqualTo("五句改")
                assertThat(it.karuta!!.shimoNoKu.fifth.kana).isEqualTo("ごくかい")
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun editWhenNotFound() {
        val karuta = createKaruta(1)

        whenever(karutaRepository.findBy(karuta.identifier)).thenReturn(null)

        actionCreator.edit(
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

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(EditMaterialAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }
}
