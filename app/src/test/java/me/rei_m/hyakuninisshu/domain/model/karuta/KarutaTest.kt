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

package me.rei_m.hyakuninisshu.domain.model.karuta

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaTest {

    private lateinit var karuta: Karuta
    private lateinit var creator: String
    private lateinit var kamiNoKu: KamiNoKu
    private lateinit var shimoNoKu: ShimoNoKu
    private lateinit var imageNo: ImageNo
    private lateinit var translation: String

    @Before
    fun setUp() {
        val identifier = KarutaIdentifier(1)
        creator = "creator"
        kamiNoKu = KamiNoKu(
                KamiNoKuIdentifier(1),
                Phrase("しょく", "初句"),
                Phrase("にく", "二句"),
                Phrase("さんく", "三句")
        )
        shimoNoKu = ShimoNoKu(
                ShimoNoKuIdentifier(1),
                Phrase("よんく", "四句"),
                Phrase("ごく", "五句")
        )
        imageNo = ImageNo("001")
        translation = "歌の訳"
        karuta = Karuta(identifier, creator, kamiNoKu, shimoNoKu, Kimariji.ONE, imageNo, translation, Color.BLUE)
    }

    @Test
    fun createInstance() {
        assertThat(karuta.creator).isEqualTo(creator)
        assertThat(karuta.kamiNoKu).isEqualTo(kamiNoKu)
        assertThat(karuta.shimoNoKu).isEqualTo(shimoNoKu)
        assertThat(karuta.kimariji).isEqualTo(Kimariji.ONE)
        assertThat(karuta.imageNo).isEqualTo(imageNo)
        assertThat(karuta.translation).isEqualTo(translation)
        assertThat(karuta.color).isEqualTo(Color.BLUE)
    }

    @Test
    fun updatePhrase() {
        val updated = karuta.updatePhrase(
                "新初句",
                "しんしょく",
                "新二句",
                "しんにく",
                "新三句",
                "しんさんく",
                "新四句",
                "しんよんく",
                "新五句",
                "しんごく"
        )
        assertThat(updated.kamiNoKu.first.kanji).isEqualTo("新初句")
        assertThat(updated.kamiNoKu.first.kana).isEqualTo("しんしょく")
        assertThat(updated.kamiNoKu.second.kanji).isEqualTo("新二句")
        assertThat(updated.kamiNoKu.second.kana).isEqualTo("しんにく")
        assertThat(updated.kamiNoKu.third.kanji).isEqualTo("新三句")
        assertThat(updated.kamiNoKu.third.kana).isEqualTo("しんさんく")
        assertThat(updated.shimoNoKu.fourth.kanji).isEqualTo("新四句")
        assertThat(updated.shimoNoKu.fourth.kana).isEqualTo("しんよんく")
        assertThat(updated.shimoNoKu.fifth.kanji).isEqualTo("新五句")
        assertThat(updated.shimoNoKu.fifth.kana).isEqualTo("しんごく")
    }
}
