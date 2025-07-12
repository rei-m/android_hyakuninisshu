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

package me.rei_m.hyakuninisshu.domain.question.model

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import java.util.concurrent.TimeUnit

/**
 * 問題コレクション.
 *
 * @param values 問題のリスト
 */
data class QuestionCollection(
    val values: List<Question>,
) {
    val wrongKarutaNoCollection: KarutaNoCollection by lazy {
        KarutaNoCollection(
            values
                .asSequence()
                .mapNotNull { question ->
                    question.state.let { state ->
                        if (state is Question.State.Answered) {
                            state.result
                        } else {
                            null
                        }
                    }
                }.filterNot { it.judgement.isCorrect }
                .map { it.judgement.karutaNo }
                .toList(),
        )
    }

    val resultSummary: QuestionResultSummary by lazy {
        val questionCount = values.size
        if (questionCount == 0) {
            QuestionResultSummary(0, 0, 0f)
        } else {
            var totalAnswerTimeMillSec: Long = 0

            var collectCount = 0

            values.forEach {
                val result =
                    it.state.let { state ->
                        if (state is Question.State.Answered) {
                            return@let state.result
                        }
                        throw IllegalStateException("Question is not finished.")
                    }

                totalAnswerTimeMillSec += result.answerMillSec
                if (result.judgement.isCorrect) {
                    collectCount++
                }
            }

            val averageAnswerTime =
                totalAnswerTimeMillSec.toFloat() / questionCount.toFloat() /
                    TimeUnit.SECONDS
                        .toMillis(
                            1,
                        ).toFloat()

            QuestionResultSummary(questionCount, collectCount, averageAnswerTime)
        }
    }
}
