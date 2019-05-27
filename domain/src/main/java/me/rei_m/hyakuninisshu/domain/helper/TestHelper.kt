package me.rei_m.hyakuninisshu.domain.helper

import androidx.annotation.VisibleForTesting
import me.rei_m.hyakuninisshu.domain.model.karuta.*
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import java.util.*

@VisibleForTesting
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
        return KarutaQuiz.createReady(
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
        return KarutaQuiz.createInAnswer(
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

    fun createAnsweredQuiz(
        correctId: Int,
        startDate: Date,
        answerMillSec: Long,
        choiceNo: ChoiceNo,
        isCorrect: Boolean
    ): KarutaQuiz {
        return KarutaQuiz.createAnswered(
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