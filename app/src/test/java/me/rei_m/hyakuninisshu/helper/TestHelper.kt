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

package me.rei_m.hyakuninisshu.helper

import me.rei_m.hyakuninisshu.domain.model.karuta.*
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import java.util.*

interface TestHelper {
    fun createKaruta(id: Int): Karuta = createKaruta(id, Color.BLUE)

    fun createKaruta(id: Int, color: Color): Karuta {
        val identifier = KarutaIdentifier(id)
        val creator = "creator"
        val kamiNoKu = KamiNoKu(
                KamiNoKuIdentifier(identifier.value),
                Phrase("しょく_$id", "初句_$id"),
                Phrase("にく_$id", "二句_$id"),
                Phrase("さんく_$id", "三句_$id")
        )
        val shimoNoKu = ShimoNoKu(
                ShimoNoKuIdentifier(identifier.value),
                Phrase("よんく_$id", "四句_$id"),
                Phrase("ごく_$id", "五句_$id")
        )
        val imageNo = ImageNo("001")
        val translation = "歌の訳"
        return Karuta(identifier, creator, kamiNoKu, shimoNoKu, Kimariji.ONE, imageNo, translation, color)
    }

    fun createQuiz(correctId: Int): KarutaQuiz {
        return KarutaQuiz(
                identifier = KarutaQuizIdentifier(),
                choiceList = listOf(
                        KarutaIdentifier(correctId),
                        KarutaIdentifier(correctId + 1),
                        KarutaIdentifier(correctId + 2),
                        KarutaIdentifier(correctId + 3)
                ),
                correctId = KarutaIdentifier(correctId)
        )
    }

    fun createQuiz(correctId: Int, startDate: Date): KarutaQuiz {
        return KarutaQuiz(
                identifier = KarutaQuizIdentifier(),
                choiceList = listOf(
                        KarutaIdentifier(correctId),
                        KarutaIdentifier(correctId + 1),
                        KarutaIdentifier(correctId + 2),
                        KarutaIdentifier(correctId + 3)
                ),
                correctId = KarutaIdentifier(correctId),
                startDate = startDate
        )
    }

    fun createQuiz(correctId: Int, startDate: Date, answerMillSec: Long, choiceNo: ChoiceNo, isCorrect: Boolean): KarutaQuiz {
        return KarutaQuiz(
                identifier = KarutaQuizIdentifier(),
                choiceList = listOf(
                        KarutaIdentifier(correctId),
                        KarutaIdentifier(correctId + 1),
                        KarutaIdentifier(correctId + 2),
                        KarutaIdentifier(correctId + 3)
                ),
                correctId = KarutaIdentifier(correctId),
                startDate = startDate,
                answerMillSec = answerMillSec,
                choiceNo = choiceNo,
                isCorrect = isCorrect
        )
    }
}
