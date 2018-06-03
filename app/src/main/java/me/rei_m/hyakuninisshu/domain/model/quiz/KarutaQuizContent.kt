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

package me.rei_m.hyakuninisshu.domain.model.quiz

import java.util.ArrayList

import me.rei_m.hyakuninisshu.domain.ValueObject
import me.rei_m.hyakuninisshu.domain.model.karuta.KamiNoKu
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaStyle
import me.rei_m.hyakuninisshu.domain.model.karuta.Phrase
import me.rei_m.hyakuninisshu.domain.model.karuta.ShimoNoKu

/**
 * 百人一首の問題内容.
 */
data class KarutaQuizContent(
        val quiz: KarutaQuiz,
        val correct: Karuta,
        private val choices: List<Karuta>,
        private val position: KarutaQuizCounter,
        val existNext: Boolean
) : ValueObject {

    val currentPosition: String = position.value

    /**
     * @param karutaStyle 表示スタイル
     * @return 読み札
     */
    fun yomiFuda(karutaStyle: KarutaStyle): YomiFuda {
        val phrase = correct.kamiNoKu
        return if (karutaStyle == KarutaStyle.KANJI)
            YomiFuda(phrase.first.kanji, phrase.second.kanji, phrase.third.kanji)
        else
            YomiFuda(phrase.first.kana, phrase.second.kana, phrase.third.kana)
    }

    /**
     * @param karutaStyle 表示スタイル
     * @return 取り札
     */
    fun toriFudas(karutaStyle: KarutaStyle): List<ToriFuda> {
        return if (karutaStyle == KarutaStyle.KANJI) {
            choices.map {
                ToriFuda(it.shimoNoKu.fourth.kanji, it.shimoNoKu.fifth.kanji)
            }
        } else {
            choices.map {
                ToriFuda(it.shimoNoKu.fourth.kana, it.shimoNoKu.fifth.kana)
            }
        }
    }
}
