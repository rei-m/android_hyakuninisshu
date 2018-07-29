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

package me.rei_m.hyakuninisshu.domain.model.quiz

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class KarutaQuizTest {

    class WhenNotStarted {

        private lateinit var karutaQuiz: KarutaQuiz

        @Before
        fun setUp() {
            karutaQuiz = KarutaQuiz(KarutaQuizIdentifier(), choiceList, correctId)
        }

        @Test
        fun createInstance() {
            assertThat(karutaQuiz.choiceList).isEqualTo(choiceList)
            assertThat(karutaQuiz.correctId).isEqualTo(correctId)
            assertThat(karutaQuiz.startDate).isNull()
            assertThat(karutaQuiz.result).isNull()
        }

        @Test
        fun start() {
            val startDate = Date()
            val actual = karutaQuiz.start(startDate)
            assertThat(actual).isEqualTo(karutaQuiz)
            assertThat(karutaQuiz.startDate).isEqualTo(startDate)
        }

        @Test(expected = IllegalStateException::class)
        fun verify() {
            karutaQuiz.verify(ChoiceNo.FIRST, Date())
        }
    }

    class WhenStarted {

        private lateinit var karutaQuiz: KarutaQuiz

        private lateinit var startDate: Date

        @Before
        fun setUp() {
            startDate = Date()
            karutaQuiz = KarutaQuiz(KarutaQuizIdentifier(), choiceList, correctId, startDate)
        }

        @Test
        fun createInstance() {
            assertThat(karutaQuiz.choiceList).isEqualTo(choiceList)
            assertThat(karutaQuiz.correctId).isEqualTo(correctId)
            assertThat(karutaQuiz.startDate).isEqualTo(startDate)
            assertThat(karutaQuiz.result).isNull()
        }

        @Test
        fun verifyWhenCorrect() {
            val answerDate = Date()
            val actual = karutaQuiz.verify(ChoiceNo.FIRST, answerDate)
            assertThat(actual).isEqualTo(karutaQuiz)
            assertThat(actual.result!!.choiceNo).isEqualTo(ChoiceNo.FIRST)
            assertThat(actual.result!!.judgement).isEqualTo(KarutaQuizJudgement(correctId, true))
            assertThat(actual.result!!.answerMillSec).isEqualTo(answerDate.time - startDate.time)
        }

        @Test
        fun verifyWhenNotCorrect() {
            val answerDate = Date()
            val actual = karutaQuiz!!.verify(ChoiceNo.SECOND, answerDate)
            assertThat(actual).isEqualTo(karutaQuiz)
            assertThat(actual.result!!.choiceNo).isEqualTo(ChoiceNo.SECOND)
            assertThat(actual.result!!.judgement).isEqualTo(KarutaQuizJudgement(correctId, false))
            assertThat(actual.result!!.answerMillSec).isEqualTo(answerDate.time - startDate.time)
        }
    }

    class WhenAnswered {

        private lateinit var karutaQuiz: KarutaQuiz

        private lateinit var startDate: Date

        @Before
        fun setUp() {
            startDate = Date()
            karutaQuiz = KarutaQuiz(KarutaQuizIdentifier(),
                    choiceList,
                    correctId,
                    startDate,
                    5000,
                    ChoiceNo.FIRST,
                    true)
        }

        @Test
        fun createInstance() {
            assertThat(karutaQuiz.choiceList).isEqualTo(choiceList)
            assertThat(karutaQuiz.correctId).isEqualTo(correctId)
            assertThat(karutaQuiz.startDate).isEqualTo(startDate)
            assertThat(karutaQuiz.result).isEqualTo(KarutaQuizResult(correctId,
                    ChoiceNo.FIRST,
                    true,
                    5000
            ))
        }
    }

    companion object {
        private val choiceList: List<KarutaIdentifier> = listOf(
                KarutaIdentifier(1),
                KarutaIdentifier(2),
                KarutaIdentifier(3),
                KarutaIdentifier(4)
        )

        private val correctId = KarutaIdentifier(1)
    }
}