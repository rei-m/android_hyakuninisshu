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

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import java.util.concurrent.TimeUnit

/**
 * 問題のコレクション.
 */
class KarutaQuizzes(val values: List<KarutaQuiz>) {

    val isEmpty: Boolean = values.isEmpty()

    /**
     * @return 解答済みの問題のうち、間違えた問題の歌のID
     */
    val wrongKarutaIds: KarutaIds
        get() = KarutaIds(
            values
                .asSequence()
                .mapNotNull { it.result }
                .filterNot { it.judgement.isCorrect }
                .map { it.judgement.karutaId }
                .toList()
        )

    /**
     * @return 解答結果を集計する
     * @throws IllegalStateException 未解答の問題があった場合
     */
    @Throws(IllegalStateException::class)
    fun resultSummary(): KarutaQuizzesResultSummary {
        val quizCount = values.size

        if (quizCount == 0) {
            return KarutaQuizzesResultSummary(0, 0, 0f)
        }

        var totalAnswerTimeMillSec: Long = 0

        var collectCount = 0

        values.forEach {
            val result = it.result ?: throw IllegalStateException("Training is not finished.")

            totalAnswerTimeMillSec += result.answerMillSec
            if (result.judgement.isCorrect) {
                collectCount++
            }
        }

        val averageAnswerTime = totalAnswerTimeMillSec.toFloat() / quizCount.toFloat() / TimeUnit.SECONDS.toMillis(1).toFloat()

        return KarutaQuizzesResultSummary(quizCount, collectCount, averageAnswerTime)
    }

    override fun equals(other: Any?): Boolean {
        other as? KarutaQuizzes ?: return false
        return values == other.values
    }

    override fun hashCode(): Int = values.hashCode()

    override fun toString(): String = "KarutaQuizzes(values=$values)"
}