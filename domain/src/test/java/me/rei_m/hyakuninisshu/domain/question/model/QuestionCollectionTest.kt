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

import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class QuestionCollectionTest : TestHelper {
    @Test
    fun wrongKarutaNoCollection() {
        val list =
            listOf(
                createQuestionReady(),
                createQuestionAnswered(),
                createQuestionAnswered(
                    isCorrect = false,
                    correctNo = KarutaNo(1),
                ),
                createQuestionAnswered(
                    isCorrect = false,
                    correctNo = KarutaNo(2),
                ),
                createQuestionAnswered(
                    isCorrect = true,
                    correctNo = KarutaNo(3),
                ),
            )
        val questionCollection = QuestionCollection(list)
        assertThat(questionCollection.wrongKarutaNoCollection).isEqualTo(
            KarutaNoCollection(listOf(KarutaNo(1), KarutaNo(2))),
        )
    }

    class WhenAllAnswered : TestHelper {
        @Test
        fun resultSummary() {
            val list =
                listOf(
                    createQuestionAnswered(
                        isCorrect = false,
                        correctNo = KarutaNo(1),
                        answerMillSec = 1000,
                    ),
                    createQuestionAnswered(
                        isCorrect = false,
                        correctNo = KarutaNo(2),
                        answerMillSec = 2000,
                    ),
                    createQuestionAnswered(
                        isCorrect = true,
                        correctNo = KarutaNo(3),
                        answerMillSec = 3000,
                    ),
                )
            val questionCollection = QuestionCollection(list)
            assertThat(questionCollection.resultSummary).isEqualTo(
                QuestionResultSummary(
                    totalQuestionCount = 3,
                    correctCount = 1,
                    averageAnswerSec = 2.0f,
                ),
            )
        }
    }

    class WhenIncludeNotAnswered : TestHelper {
        @Test(expected = IllegalStateException::class)
        fun resultSummary() {
            val list =
                listOf(
                    createQuestionReady(),
                    createQuestionAnswered(
                        isCorrect = false,
                        correctNo = KarutaNo(2),
                        answerMillSec = 2000,
                    ),
                    createQuestionAnswered(
                        isCorrect = true,
                        correctNo = KarutaNo(3),
                        answerMillSec = 3000,
                    ),
                )
            QuestionCollection(list).resultSummary
        }
    }
}
