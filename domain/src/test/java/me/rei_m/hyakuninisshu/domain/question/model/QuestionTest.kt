/*
 * Copyright (c) 2020. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package me.rei_m.hyakuninisshu.domain.question.model

import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class QuestionTest {
    class WhenStateIsReady : TestHelper {
        private lateinit var question: Question

        @Before
        fun setUp() {
            question = createQuestionReady()
        }

        @Test
        fun start() {
            val actual = question.start(Date()).state
            assertThat(actual).isInstanceOf(Question.State.InAnswer::class.java)
        }

        @Test(expected = IllegalStateException::class)
        fun verify() {
            question.verify(KarutaNo(1), Date())
        }
    }

    class WhenStateIsInAnswer : TestHelper {
        lateinit var question: Question

        @Before
        fun setUp() {
            question = createQuestionInAnswer()
        }

        @Test
        fun start() {
            val actual = question.start(Date()).state
            assertThat(actual).isInstanceOf(Question.State.InAnswer::class.java)
        }

        @Test
        fun verify() {
            val actual = question.verify(KarutaNo(1), Date()).state
            assertThat(actual).isInstanceOf(Question.State.Answered::class.java)
        }
    }

    class WhenStateIsAnswered : TestHelper {
        lateinit var question: Question

        @Before
        fun setUp() {
            question = createQuestionAnswered()
        }

        @Test(expected = IllegalStateException::class)
        fun start() {
            question.start(Date())
        }

        @Test(expected = IllegalStateException::class)
        fun verify() {
            question.verify(KarutaNo(1), Date())
        }
    }
}
