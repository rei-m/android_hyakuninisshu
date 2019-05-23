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
package me.rei_m.hyakuninisshu.action.quiz

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizCounter
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.Date

class QuizActionCreatorTest : TestHelper {

    private lateinit var actionCreator: QuizActionCreator

    private lateinit var karutaRepository: KarutaRepository
    private lateinit var karutaQuizRepository: KarutaQuizRepository

    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = mock {}
        karutaRepository = mock {}
        karutaQuizRepository = mock { }
        actionCreator = QuizActionCreator(
            karutaRepository,
            karutaQuizRepository,
            dispatcher
        )
    }

    @Test
    fun fetch() {
        val quiz = createQuiz(1)
        whenever(karutaQuizRepository.findBy(quiz.identifier)).thenReturn(quiz)
        whenever(karutaQuizRepository.countQuizByAnswered()).thenReturn(KarutaQuizCounter(10, 1))
        quiz.choiceList.forEach {
            val karuta = createKaruta(id = it.value)
            whenever(karutaRepository.findBy(it)).thenReturn(karuta)
        }

        actionCreator.fetch(quiz.identifier)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchQuizAction::class.java)
            if (it is FetchQuizAction) {
                assertThat(it.karutaQuizContent?.quiz).isEqualTo(quiz)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchWhenNotFound() {
        val quizId = KarutaQuizIdentifier()
        whenever(karutaQuizRepository.findBy(quizId)).thenReturn(null)

        actionCreator.fetch(quizId)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchQuizAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun start() {
        val quiz = createQuiz(1)
        val startDate = Date()
        whenever(karutaQuizRepository.findBy(quiz.identifier)).thenReturn(quiz)
        whenever(karutaQuizRepository.countQuizByAnswered()).thenReturn(KarutaQuizCounter(10, 1))
        quiz.choiceList.forEach {
            val karuta = createKaruta(id = it.value)
            whenever(karutaRepository.findBy(it)).thenReturn(karuta)
        }
        whenever(karutaQuizRepository.store(any())).thenAnswer { }

        actionCreator.start(quiz.identifier, startDate)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartQuizAction::class.java)
            if (it is StartQuizAction) {
                assertThat(it.karutaQuizContent?.quiz?.startDate).isEqualTo(startDate)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun startWhenNotFoundQuiz() {
        val quiz = createQuiz(1)
        val startDate = Date()
        whenever(karutaQuizRepository.findBy(quiz.identifier)).thenReturn(null)

        actionCreator.start(quiz.identifier, startDate)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartQuizAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun answer() {
        val quiz = createStartedQuiz(1, Date())
        val answerDate = Date()
        whenever(karutaQuizRepository.findBy(quiz.identifier)).thenReturn(quiz)
        whenever(karutaQuizRepository.countQuizByAnswered()).thenReturn(KarutaQuizCounter(10, 1))
        quiz.choiceList.forEach {
            val karuta = createKaruta(id = it.value)
            whenever(karutaRepository.findBy(it)).thenReturn(karuta)
        }
        whenever(karutaQuizRepository.store(any())).thenAnswer { }

        actionCreator.answer(quiz.identifier, ChoiceNo.FIRST, answerDate)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(AnswerQuizAction::class.java)
            if (it is AnswerQuizAction) {
                assertThat(it.karutaQuizContent?.quiz?.result).isNotNull
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun answerWhenNotFoundQuiz() {
        val quiz = createStartedQuiz(1, Date())
        val answerDate = Date()
        whenever(karutaQuizRepository.findBy(quiz.identifier)).thenReturn(null)

        actionCreator.answer(quiz.identifier, ChoiceNo.FIRST, answerDate)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(AnswerQuizAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }
}