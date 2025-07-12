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

package me.rei_m.hyakuninisshu.state.question.action

import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import me.rei_m.hyakuninisshu.domain.question.model.QuestionId
import me.rei_m.hyakuninisshu.domain.question.model.QuestionRepository
import me.rei_m.hyakuninisshu.state.question.model.ToriFuda
import me.rei_m.hyakuninisshu.state.training.model.DisplayStyleCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class QuestionActionCreatorTest : TestHelper {
    private lateinit var actionCreator: QuestionActionCreator
    private lateinit var karutaRepository: KarutaRepository
    private lateinit var questionRepository: QuestionRepository
    private val questionId = QuestionId("1")

    @Before
    fun setUp() {
        karutaRepository = mock {}
        questionRepository = mock { }
        actionCreator =
            QuestionActionCreator(
                context = ApplicationProvider.getApplicationContext(),
                karutaRepository = karutaRepository,
                questionRepository = questionRepository,
            )
    }

    @Test
    fun start_success() =
        runBlocking {
            val question =
                createQuestionReady(
                    id = questionId,
                )
            val choiceList = question.choiceList
            whenever(questionRepository.findById(questionId)).thenReturn(question)
            whenever(questionRepository.count()).thenReturn(10)
            whenever(karutaRepository.findAllWithNo(choiceList)).thenReturn(createAllKarutaList())

            val actual =
                actionCreator.start("1", DisplayStyleCondition.KANJI, DisplayStyleCondition.KANA)
            assertThat(actual).isInstanceOf(StartQuestionAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun start_failure() =
        runBlocking {
            val question =
                createQuestionReady(
                    id = questionId,
                )
            val choiceList = question.choiceList
            whenever(questionRepository.findById(questionId)).thenReturn(null)
            whenever(questionRepository.count()).thenReturn(10)
            whenever(karutaRepository.findAllWithNo(choiceList)).thenReturn(createAllKarutaList())

            val actual =
                actionCreator.start("1", DisplayStyleCondition.KANJI, DisplayStyleCondition.KANA)
            assertThat(actual).isInstanceOf(StartQuestionAction.Failure::class.java)
            return@runBlocking
        }

    @Test
    fun startAnswer_success() =
        runBlocking {
            val question =
                createQuestionReady(
                    id = questionId,
                )
            whenever(questionRepository.findById(questionId)).thenReturn(question)
            whenever(questionRepository.save(question)).thenAnswer { }
            whenever(karutaRepository.findByNo(question.correctNo)).thenReturn(createKaruta())

            val actual = actionCreator.startAnswer("1", Date())
            assertThat(actual).isInstanceOf(StartAnswerQuestionAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun startAnswer_failure() =
        runBlocking {
            val question =
                createQuestionReady(
                    id = questionId,
                )
            whenever(questionRepository.findById(questionId)).thenReturn(null)
            whenever(questionRepository.save(question)).thenAnswer { }
            whenever(karutaRepository.findByNo(question.correctNo)).thenReturn(createKaruta())

            val actual = actionCreator.startAnswer("1", Date())
            assertThat(actual).isInstanceOf(StartAnswerQuestionAction.Failure::class.java)
            return@runBlocking
        }

    @Test
    fun answer_success() =
        runBlocking {
            val question =
                createQuestionInAnswer(
                    id = questionId,
                )
            whenever(questionRepository.findById(questionId)).thenReturn(question)
            whenever(questionRepository.save(question)).thenAnswer { }
            whenever(karutaRepository.findByNo(question.correctNo)).thenReturn(createKaruta())

            val actual =
                actionCreator.answer(
                    "1",
                    ToriFuda(
                        karutaNo = question.choiceList[0].value,
                        firstLine = "aaaaa",
                        secondLine = "bbbbb",
                    ),
                    answerDate = Date(),
                )
            assertThat(actual).isInstanceOf(AnswerQuestionAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun answer_failure() =
        runBlocking {
            val question =
                createQuestionInAnswer(
                    id = questionId,
                )
            whenever(questionRepository.findById(questionId)).thenReturn(null)
            whenever(questionRepository.save(question)).thenAnswer { }
            whenever(karutaRepository.findByNo(question.correctNo)).thenReturn(createKaruta())

            val actual =
                actionCreator.answer(
                    "1",
                    ToriFuda(
                        karutaNo = question.choiceList[0].value,
                        firstLine = "aaaaa",
                        secondLine = "bbbbb",
                    ),
                    answerDate = Date(),
                )
            assertThat(actual).isInstanceOf(AnswerQuestionAction.Failure::class.java)
            return@runBlocking
        }
}
