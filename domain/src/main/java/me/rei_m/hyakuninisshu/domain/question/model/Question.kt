/*
 * Copyright (c) 2020. Rei Matsushita
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

package me.rei_m.hyakuninisshu.domain.question.model

import me.rei_m.hyakuninisshu.domain.AbstractEntity
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import java.util.Date

/**
 * 百人一首の問題.
 *
 * @param id ID
 * @param no 何番目の問題か
 * @param choiceList 選択肢の歌番号のリスト
 * @param state 問題の状態
 */
class Question constructor(
    id: QuestionId,
    val no: Int,
    val choiceList: List<KarutaNo>,
    val correctNo: KarutaNo,
    state: State
) : AbstractEntity<QuestionId>(id) {

    var state: State = state
        private set

    /**
     * 解答を開始する.
     *
     * @param startDate 解答開始時間
     *
     * @return 問題
     *
     * @throws IllegalStateException すでに回答済だった場合
     */
    @Throws(IllegalStateException::class)
    fun start(startDate: Date): Question {
        if (state is State.Answered) {
            throw IllegalStateException("Question is already answered.")
        }
        this.state = State.InAnswer(startDate)
        return this
    }

    /**
     * 選択肢が正解か判定する.
     *
     * @param selectedNo 選択した歌の番号
     * @param answerDate 解答した時間
     *
     * @return 解答後の問題
     *
     * @throws IllegalStateException 解答開始していない場合, すでに回答済の場合.
     */
    @Throws(IllegalStateException::class)
    fun verify(selectedNo: KarutaNo, answerDate: Date): Question {
        when (val current = state) {
            is State.Ready -> {
                throw IllegalStateException("Question is not started. Call start.")
            }
            is State.InAnswer -> {
                val answerTime = answerDate.time - current.startDate.time
                val judgement = QuestionJudgement(
                    correctNo,
                    correctNo == selectedNo
                )
                this.state = State.Answered(
                    current.startDate,
                    QuestionResult(selectedNo, answerTime, judgement)
                )
                return this
            }
            is State.Answered -> {
                throw IllegalStateException("Question is already answered.")
            }
        }
    }

    override fun toString() =
        "Question(id=$identifier no=$no, choiceList=$choiceList, correctNo=$correctNo, state=$state)"

    /**
     * 問題の状態.
     *
     * Ready: 開始前
     * InAnswer: 回答中
     * Answered: 回答済
     */
    sealed class State {
        object Ready : State()
        class InAnswer(val startDate: Date) : State()
        class Answered(val startDate: Date, val result: QuestionResult) : State()

        companion object {
            fun create(startDate: Date?, result: QuestionResult?): State {
                return if (startDate != null) {
                    if (result != null) {
                        Answered(startDate, result)
                    } else {
                        InAnswer(startDate)
                    }
                } else {
                    Ready
                }
            }
        }
    }

    companion object {
        const val CHOICE_SIZE = 4
    }
}
