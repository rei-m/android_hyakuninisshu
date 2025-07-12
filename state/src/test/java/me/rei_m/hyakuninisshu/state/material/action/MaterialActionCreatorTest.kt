/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.state.material.action

import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaColor
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import me.rei_m.hyakuninisshu.domain.karuta.model.Kimariji
import me.rei_m.hyakuninisshu.state.material.model.ColorFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MaterialActionCreatorTest : TestHelper {
    private lateinit var actionCreator: MaterialActionCreator
    private lateinit var karutaRepository: KarutaRepository

    @Before
    fun setUp() {
        karutaRepository = mock {}
        actionCreator =
            MaterialActionCreator(
                context = ApplicationProvider.getApplicationContext(),
                karutaRepository = karutaRepository,
            )
    }

    @Test
    fun fetchMaterialList_success_withoutColor() =
        runBlocking {
            whenever(
                karutaRepository.findAllWithCondition(
                    fromNo = KarutaNo.MIN,
                    toNo = KarutaNo.MAX,
                    kimarijis = Kimariji.values().toList(),
                    colors = KarutaColor.values().toList(),
                ),
            ).thenReturn(createAllKarutaList())

            val actual = actionCreator.fetchMaterialList(null)
            assertThat(actual).isInstanceOf(FetchMaterialListAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun fetchMaterialList_success_withColor() =
        runBlocking {
            whenever(
                karutaRepository.findAllWithCondition(
                    fromNo = KarutaNo.MIN,
                    toNo = KarutaNo.MAX,
                    kimarijis = Kimariji.values().toList(),
                    colors = listOf(KarutaColor.BLUE),
                ),
            ).thenReturn(createAllKarutaList())

            val actual = actionCreator.fetchMaterialList(ColorFilter.BLUE)
            assertThat(actual).isInstanceOf(FetchMaterialListAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun fetchMaterialList_failure() =
        runBlocking {
            whenever(
                karutaRepository.findAllWithCondition(
                    fromNo = KarutaNo.MIN,
                    toNo = KarutaNo.MAX,
                    kimarijis = Kimariji.values().toList(),
                    colors = KarutaColor.values().toList(),
                ),
            ).thenThrow(RuntimeException())

            val actual = actionCreator.fetchMaterialList(null)
            assertThat(actual).isInstanceOf(FetchMaterialListAction.Failure::class.java)
            return@runBlocking
        }

    @Test
    fun edit_success() =
        runBlocking {
            val karuta = createKaruta()
            whenever(karutaRepository.findByNo(karuta.no)).thenReturn(karuta)
            whenever(karutaRepository.save(any())).thenAnswer {}

            val actual =
                actionCreator.edit(
                    karutaNo = karuta.no.value,
                    firstPhraseKanji = "firstPhraseKanji",
                    firstPhraseKana = "firstPhraseKana",
                    secondPhraseKanji = "secondPhraseKanji",
                    secondPhraseKana = "secondPhraseKana",
                    thirdPhraseKanji = "thirdPhraseKanji",
                    thirdPhraseKana = "thirdPhraseKana",
                    fourthPhraseKanji = "fourthPhraseKanji",
                    fourthPhraseKana = "fourthPhraseKana",
                    fifthPhraseKanji = "fifthPhraseKanji",
                    fifthPhraseKana = "fifthPhraseKana",
                )
            assertThat(actual).isInstanceOf(EditMaterialAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun edit_failure() =
        runBlocking {
            val karuta = createKaruta()
            whenever(karutaRepository.findByNo(karuta.no)).thenReturn(karuta)
            whenever(karutaRepository.save(any())).thenThrow(RuntimeException())

            val actual =
                actionCreator.edit(
                    karutaNo = karuta.no.value,
                    firstPhraseKanji = "firstPhraseKanji",
                    firstPhraseKana = "firstPhraseKana",
                    secondPhraseKanji = "secondPhraseKanji",
                    secondPhraseKana = "secondPhraseKana",
                    thirdPhraseKanji = "thirdPhraseKanji",
                    thirdPhraseKana = "thirdPhraseKana",
                    fourthPhraseKanji = "fourthPhraseKanji",
                    fourthPhraseKana = "fourthPhraseKana",
                    fifthPhraseKanji = "fifthPhraseKanji",
                    fifthPhraseKana = "fifthPhraseKana",
                )
            assertThat(actual).isInstanceOf(EditMaterialAction.Failure::class.java)
            return@runBlocking
        }
}
