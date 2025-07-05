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

package me.rei_m.hyakuninisshu.domain.helper

import androidx.annotation.VisibleForTesting
import me.rei_m.hyakuninisshu.domain.karuta.model.KamiNoKu
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaColor
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaId
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaImageNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import me.rei_m.hyakuninisshu.domain.karuta.model.Kimariji
import me.rei_m.hyakuninisshu.domain.karuta.model.ShimoNoKu
import me.rei_m.hyakuninisshu.domain.karuta.model.Verse
import me.rei_m.hyakuninisshu.domain.question.model.Exam
import me.rei_m.hyakuninisshu.domain.question.model.ExamId
import me.rei_m.hyakuninisshu.domain.question.model.ExamResult
import me.rei_m.hyakuninisshu.domain.question.model.Question
import me.rei_m.hyakuninisshu.domain.question.model.QuestionId
import me.rei_m.hyakuninisshu.domain.question.model.QuestionJudgement
import me.rei_m.hyakuninisshu.domain.question.model.QuestionResult
import me.rei_m.hyakuninisshu.domain.question.model.QuestionResultSummary
import java.util.Date

@VisibleForTesting
interface TestHelper {
    fun createKaruta(
        id: KarutaId = KarutaId(1),
        no: KarutaNo = KarutaNo(1),
        creator: String = "天智天皇",
        kamiNoKu: KamiNoKu =
            KamiNoKu(
                karutaNo = KarutaNo(1),
                shoku =
                    Verse(
                        kana = "あきのたの",
                        kanji = "秋の田の",
                    ),
                niku =
                    Verse(
                        kana = "かりほのいほの",
                        kanji = "かりほの庵の",
                    ),
                sanku =
                    Verse(
                        kana = "とまをあらみ",
                        kanji = "苫をあらみ",
                    ),
            ),
        shimoNoKu: ShimoNoKu =
            ShimoNoKu(
                karutaNo = KarutaNo(1),
                shiku =
                    Verse(
                        kana = "わがころもでは",
                        kanji = "わが衣手は",
                    ),
                goku =
                    Verse(
                        kana = "つゆにぬれつつ",
                        kanji = "露にぬれつつ",
                    ),
            ),
        kimariji: Kimariji = Kimariji.THREE,
        imageNo: KarutaImageNo = KarutaImageNo("001"),
        translation: String = "秋の田の側につくった仮小屋に泊まってみると、屋根をふいた苫の目があらいので、その隙間から忍びこむ冷たい夜露が、私の着物の袖をすっかりと濡らしてしまっているなぁ。",
        color: KarutaColor = KarutaColor.BLUE,
    ): Karuta =
        Karuta(
            id = id,
            no = no,
            creator = creator,
            kamiNoKu = kamiNoKu,
            shimoNoKu = shimoNoKu,
            kimariji = kimariji,
            imageNo = imageNo,
            translation = translation,
            color = color,
        )

    fun createAllKarutaList(): List<Karuta> =
        KarutaNo.LIST.map {
            createKaruta(
                id = KarutaId(it.value),
                no = it,
            )
        }

    fun createQuestionReady(
        id: QuestionId = QuestionId(),
        no: Int = 1,
        choiceList: List<KarutaNo> =
            listOf(
                KarutaNo(1),
                KarutaNo(2),
                KarutaNo(3),
                KarutaNo(4),
                KarutaNo(5),
                KarutaNo(6),
                KarutaNo(7),
                KarutaNo(8),
                KarutaNo(9),
            ),
        correctNo: KarutaNo = KarutaNo(3),
    ): Question =
        Question(
            id = id,
            no = no,
            choiceList = choiceList,
            correctNo = correctNo,
            state = Question.State.Ready,
        )

    fun createQuestionInAnswer(
        id: QuestionId = QuestionId(),
        no: Int = 1,
        choiceList: List<KarutaNo> =
            listOf(
                KarutaNo(1),
                KarutaNo(2),
                KarutaNo(3),
                KarutaNo(4),
                KarutaNo(5),
                KarutaNo(6),
                KarutaNo(7),
                KarutaNo(8),
                KarutaNo(9),
            ),
        correctNo: KarutaNo = KarutaNo(3),
        startDate: Date = Date(),
    ): Question =
        Question(
            id = id,
            no = no,
            choiceList = choiceList,
            correctNo = correctNo,
            state = Question.State.InAnswer(startDate = startDate),
        )

    fun createQuestionAnswered(
        id: QuestionId = QuestionId(),
        no: Int = 1,
        choiceList: List<KarutaNo> =
            listOf(
                KarutaNo(1),
                KarutaNo(2),
                KarutaNo(3),
                KarutaNo(4),
                KarutaNo(5),
                KarutaNo(6),
                KarutaNo(7),
                KarutaNo(8),
                KarutaNo(9),
            ),
        correctNo: KarutaNo = KarutaNo(3),
        startDate: Date = Date(),
        selectedKarutaNo: KarutaNo = KarutaNo(3),
        answerMillSec: Long = 9800,
        isCorrect: Boolean = true,
    ): Question =
        Question(
            id = id,
            no = no,
            choiceList = choiceList,
            correctNo = correctNo,
            state =
                Question.State.Answered(
                    startDate = startDate,
                    result =
                        QuestionResult(
                            selectedKarutaNo = selectedKarutaNo,
                            answerMillSec = answerMillSec,
                            judgement =
                                QuestionJudgement(
                                    karutaNo = correctNo,
                                    isCorrect = isCorrect,
                                ),
                        ),
                ),
        )

    fun createExam(
        tookDate: Date = Date(),
        totalQuestionCount: Int = 10,
        correctCount: Int = 9,
        averageAnswerSec: Float = 2.67f,
        wrongKarutaNoCollection: KarutaNoCollection = KarutaNoCollection(listOf(KarutaNo(1))),
    ): Exam =
        Exam(
            id = ExamId(generateExamId()),
            tookDate = tookDate,
            result =
                ExamResult(
                    resultSummary =
                        QuestionResultSummary(
                            totalQuestionCount = totalQuestionCount,
                            correctCount = correctCount,
                            averageAnswerSec = averageAnswerSec,
                        ),
                    wrongKarutaNoCollection = wrongKarutaNoCollection,
                ),
        )

    companion object {
        @Volatile
        private var examIdSequence: Long = 1

        private fun generateExamId(): Long {
            synchronized(this) {
                examIdSequence += 1
                return examIdSequence
            }
        }
    }
}
