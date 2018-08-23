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

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.helper.TestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.Date

class KarutaQuizzesTest {

    class WhenAllFinished : TestHelper {

        private lateinit var karutaQuizzes: KarutaQuizzes

        private lateinit var value: List<KarutaQuiz>

        @Before
        fun setUp() {
            value = listOf(
                createQuiz(1),
                createQuiz(2),
                createQuiz(3),
                createQuiz(4),
                createQuiz(5)
            )
            value.forEachIndexed { i, v ->
                val startDate = Date()
                val answerDate = Date(startDate.time + 5000)
                if (i % 2 == 0) {
                    v.start(startDate).verify(ChoiceNo.FIRST, answerDate)
                } else {
                    v.start(startDate).verify(ChoiceNo.SECOND, answerDate)
                }
            }
            karutaQuizzes = KarutaQuizzes(value)
        }

        @Test
        fun createInstance() {
            assertThat(karutaQuizzes.values).isEqualTo(value)
            assertThat(karutaQuizzes.isEmpty).isFalse()
        }

        @Test
        fun wrongKarutaIds() {
            val expected = KarutaIds(
                listOf(
                    KarutaIdentifier(2),
                    KarutaIdentifier(4)
                )
            )
            assertThat(karutaQuizzes.wrongKarutaIds).isEqualTo(expected)
        }

        @Test
        fun resultSummary() {
            val expected = KarutaQuizzesResultSummary(
                5,
                3,
                5.0f
            )
            assertThat(karutaQuizzes.resultSummary()).isEqualTo(expected)
        }
    }

    class WhenEmpty {

        private lateinit var karutaQuizzes: KarutaQuizzes

        private lateinit var value: List<KarutaQuiz>

        @Before
        fun setUp() {
            value = listOf()
            karutaQuizzes = KarutaQuizzes(value)
        }

        @Test
        fun createInstance() {
            assertThat(karutaQuizzes.values).isEqualTo(value)
            assertThat(karutaQuizzes.isEmpty).isTrue()
        }

        @Test
        fun wrongKarutaIds() {
            val expected = KarutaIds(listOf())
            assertThat(karutaQuizzes.wrongKarutaIds).isEqualTo(expected)
        }

        @Test
        fun resultSummary() {
            val expected = KarutaQuizzesResultSummary(
                0,
                0,
                0.0f
            )
            assertThat(karutaQuizzes.resultSummary()).isEqualTo(expected)
        }
    }

    class WhenNotFinished : TestHelper {
        private lateinit var karutaQuizzes: KarutaQuizzes

        private lateinit var value: List<KarutaQuiz>

        @Before
        fun setUp() {
            value = listOf(
                createQuiz(1),
                createQuiz(2)
            )
            value.first().start(Date()).verify(ChoiceNo.FIRST, Date())
            karutaQuizzes = KarutaQuizzes(value)
        }

        @Test(expected = IllegalStateException::class)
        fun resultSummary() {
            karutaQuizzes.resultSummary()
        }
    }
}
