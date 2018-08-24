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
package me.rei_m.hyakuninisshu.domain.model.quiz

import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaStyle
import me.rei_m.hyakuninisshu.helper.TestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaQuizContentTest : TestHelper {

    private lateinit var karutaQuizContent: KarutaQuizContent

    private lateinit var correct: Karuta

    private lateinit var karutaQuiz: KarutaQuiz

    @Before
    fun setUp() {
        correct = createKaruta(id = 1)
        karutaQuiz = createQuiz(1)
        val choices = karutaQuiz.choiceList.map { createKaruta(id = it.value) }

        val counter = KarutaQuizCounter(100, 10)
        karutaQuizContent = KarutaQuizContent(karutaQuiz, correct, choices, counter, true)
    }

    @Test
    fun createInstance() {
        assertThat(karutaQuizContent.quiz).isEqualTo(karutaQuiz)
        assertThat(karutaQuizContent.correct).isEqualTo(correct)
        assertThat(karutaQuizContent.existNext).isEqualTo(true)
        assertThat(karutaQuizContent.currentPosition).isEqualTo("10 / 100")
    }

    @Test
    fun yomiFudaByKana() {
        val expected = YomiFuda("しょく_1", "にく_1", "さんく_1")
        assertThat(karutaQuizContent.yomiFuda(KarutaStyle.KANA)).isEqualTo(expected)
    }

    @Test
    fun yomiFudaByKanji() {
        val expected = YomiFuda("初句_1", "二句_1", "三句_1")
        assertThat(karutaQuizContent.yomiFuda(KarutaStyle.KANJI)).isEqualTo(expected)
    }

    @Test
    fun toriFudasByKana() {
        val expected = listOf(
                ToriFuda("よんく_1", "ごく_1"),
                ToriFuda("よんく_2", "ごく_2"),
                ToriFuda("よんく_3", "ごく_3"),
                ToriFuda("よんく_4", "ごく_4")
        )
        assertThat(karutaQuizContent.toriFudas(KarutaStyle.KANA)).isEqualTo(expected)
    }

    @Test
    fun toriFudasByKanji() {
        val expected = listOf(
                ToriFuda("四句_1", "五句_1"),
                ToriFuda("四句_2", "五句_2"),
                ToriFuda("四句_3", "五句_3"),
                ToriFuda("四句_4", "五句_4")
        )
        assertThat(karutaQuizContent.toriFudas(KarutaStyle.KANJI)).isEqualTo(expected)
    }
}
