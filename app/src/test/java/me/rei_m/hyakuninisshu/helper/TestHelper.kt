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
    fun createKaruta(
            id: Int,
            firstKanji: String = "初句_$id",
            firstKana: String = "しょく_$id",
            secondKanji: String = "二句_$id",
            secondKana: String = "にく_$id",
            thirdKanji: String = "三句_$id",
            thirdKana: String = "さんく_$id",
            fourthKanji: String = "四句_$id",
            fourthKana: String = "よんく_$id",
            fifthKanji: String = "五句_$id",
            fifthKana: String = "ごく_$id",
            translation: String = "歌の訳",
            creator: String = "creator",
            kimariji: Kimariji = Kimariji.ONE,
            color: Color = Color.BLUE
    ): Karuta {
        val identifier = KarutaIdentifier(id)
        val kamiNoKu = KamiNoKu(
                KamiNoKuIdentifier(identifier.value),
                Phrase(firstKana, firstKanji),
                Phrase(secondKana, secondKanji),
                Phrase(thirdKana, thirdKanji)
        )
        val shimoNoKu = ShimoNoKu(
                ShimoNoKuIdentifier(identifier.value),
                Phrase(fourthKana, fourthKanji),
                Phrase(fifthKana, fifthKanji)
        )
        val imageNo = ImageNo("001")

        return Karuta(identifier, creator, kamiNoKu, shimoNoKu, kimariji, imageNo, translation, color)
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

    fun createStartedQuiz(correctId: Int, startDate: Date): KarutaQuiz {
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

    fun createAnsweredQuiz(correctId: Int, startDate: Date, answerMillSec: Long, choiceNo: ChoiceNo, isCorrect: Boolean): KarutaQuiz {
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
