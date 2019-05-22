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

import me.rei_m.hyakuninisshu.domain.AbstractEntity
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import java.util.Date

/**
 * 百人一首の問題.
 */
class KarutaQuiz private constructor(
    identifier: KarutaQuizIdentifier,
    val choiceList: List<KarutaIdentifier>,
    val correctId: KarutaIdentifier,
    startDate: Date? = null,
    result: KarutaQuizResult? = null
) : AbstractEntity<KarutaQuizIdentifier>(identifier) {

    var startDate: Date? = startDate
        private set

    var result: KarutaQuizResult? = result
        private set

    val state: State
        get() = if (startDate == null && result == null) {
            State.READY
        } else if (startDate != null && result == null) {
            State.IN_ANSWER
        } else {
            State.ANSWERED
        }

    /**
     * 解答を開始する.
     *
     * @param startDate 解答開始時間
     * @return 問題
     */
    fun start(startDate: Date): KarutaQuiz {
        this.startDate = startDate
        return this
    }

    /**
     * 選択肢が正解か判定する.
     *
     * @param choiceNo 選択肢番号
     * @param answerDate 解答した時間
     * @return 解答後の問題
     * @throws IllegalStateException 解答開始していない場合
     */
    @Throws(IllegalStateException::class)
    fun verify(choiceNo: ChoiceNo, answerDate: Date): KarutaQuiz {
        val startTime = startDate?.time ?: let {
            throw IllegalStateException("Quiz is not started. Call start.")
        }
        val answerTime = answerDate.time - startTime
        val selectedId = choiceList[choiceNo.asIndex]
        val isCorrect = correctId == selectedId
        this.result = KarutaQuizResult(correctId, choiceNo, isCorrect, answerTime)
        return this
    }

    override fun toString() =
        "KarutaQuiz(choiceList=$choiceList, correctId=$correctId, startDate=$startDate, result=$result)"

    enum class State {
        READY, IN_ANSWER, ANSWERED,
    }

    companion object {
        fun createReady(
            identifier: KarutaQuizIdentifier,
            choiceList: List<KarutaIdentifier>,
            correctId: KarutaIdentifier
        ) = KarutaQuiz(identifier, choiceList, correctId)

        fun createInAnswer(
            identifier: KarutaQuizIdentifier,
            choiceList: List<KarutaIdentifier>,
            correctId: KarutaIdentifier,
            startDate: Date
        ) = KarutaQuiz(identifier, choiceList, correctId, startDate)

        fun createAnswered(
            identifier: KarutaQuizIdentifier,
            choiceList: List<KarutaIdentifier>,
            correctId: KarutaIdentifier,
            startDate: Date,
            answerMillSec: Long,
            choiceNo: ChoiceNo,
            isCorrect: Boolean
        ): KarutaQuiz {

            val result = KarutaQuizResult(
                correctId,
                choiceNo,
                isCorrect,
                answerMillSec
            )

            return KarutaQuiz(identifier, choiceList, correctId, startDate, result)
        }
    }
}
