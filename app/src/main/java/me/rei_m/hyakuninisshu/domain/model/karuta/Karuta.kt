/*
 * Copyright (c) 2017. Rei Matsushita
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

import me.rei_m.hyakuninisshu.domain.AbstractEntity

/**
 * 百人一首の歌.
 */
class Karuta(identifier: KarutaIdentifier,
             val creator: String,
             var kamiNoKu: KamiNoKu,
             var shimoNoKu: ShimoNoKu,
             val kimariji: Kimariji,
             val imageNo: ImageNo,
             val translation: String,
             val color: Color) : AbstractEntity<Karuta, KarutaIdentifier>(identifier) {

    fun updatePhrase(firstPhraseKanji: String,
                     firstPhraseKana: String,
                     secondPhraseKanji: String,
                     secondPhraseKana: String,
                     thirdPhraseKanji: String,
                     thirdPhraseKana: String,
                     fourthPhraseKanji: String,
                     fourthPhraseKana: String,
                     fifthPhraseKanji: String,
                     fifthPhraseKana: String): Karuta {

        val kamiNoKu = KamiNoKu(
                this.kamiNoKu.identifier(),
                Phrase(firstPhraseKana, firstPhraseKanji),
                Phrase(secondPhraseKana, secondPhraseKanji),
                Phrase(thirdPhraseKana, thirdPhraseKanji)
        )

        val shimoNoKu = ShimoNoKu(
                this.shimoNoKu.identifier(),
                Phrase(fourthPhraseKana, fourthPhraseKanji),
                Phrase(fifthPhraseKana, fifthPhraseKanji)
        )

        this.kamiNoKu = kamiNoKu
        this.shimoNoKu = shimoNoKu

        return this
    }

    override fun toString(): String {
        return """
            Karuta(
                creator='$creator',
                kamiNoKu=$kamiNoKu,
                shimoNoKu=$shimoNoKu,
                kimariji=$kimariji,
                imageNo=$imageNo,
                translation='$translation',
                color=$color
            )
        """
    }

    companion object {
        const val NUMBER_OF_KARUTA = 100
    }
}
