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
package me.rei_m.hyakuninisshu.action.training

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExams
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzesResultSummary
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class TrainingActionCreatorTest : TestHelper {

    private lateinit var actionCreator: TrainingActionCreator

    private lateinit var karutaRepository: KarutaRepository
    private lateinit var karutaQuizRepository: KarutaQuizRepository
    private lateinit var karutaExamRepository: KarutaExamRepository

    @Before
    fun setUp() {
        karutaRepository = mock {}
        karutaQuizRepository = mock {}
        karutaExamRepository = mock {}
        actionCreator = TrainingActionCreator(
            karutaRepository,
            karutaQuizRepository,
            karutaExamRepository
        )
    }

    @Test
    fun start() {
        val fromId = KarutaIdentifier(1)
        val toId = KarutaIdentifier(10)
        val kimariji = Kimariji.ONE
        val color = Color.BLUE
        val karutas = Karutas((0..10).map { createKaruta(it) })

        whenever(karutaRepository.findIds(fromId, toId, color, kimariji))
            .thenReturn(KarutaIds(listOf(KarutaIdentifier(1))))
        whenever(karutaRepository.list()).thenReturn(karutas)
        whenever(karutaQuizRepository.initialize(any())).thenAnswer { }

        val actual = actionCreator.start(fromId, toId, kimariji, color)

        assertThat(actual.karutaQuizId).isNotNull
        assertThat(actual.error).isNull()
    }

    @Test
    fun startForExam() {
        val examId = KarutaExamIdentifier(1)
        val exam = KarutaExam(
            identifier = examId,
            tookDate = Date(),
            result = KarutaExamResult(
                resultSummary = KarutaQuizzesResultSummary(
                    quizCount = 100,
                    correctCount = 100,
                    averageAnswerSec = 5f
                ),
                wrongKarutaIds = KarutaIds(listOf(KarutaIdentifier(1), KarutaIdentifier(5)))
            )
        )

        val karutas = Karutas((0..10).map { createKaruta(it) })

        whenever(karutaExamRepository.list()).thenReturn(KarutaExams(listOf(exam)))
        whenever(karutaRepository.list()).thenReturn(karutas)
        whenever(karutaQuizRepository.initialize(any())).thenAnswer { }

        val actual = actionCreator.startForExam()

        assertThat(actual.karutaQuizId).isNotNull
        assertThat(actual.error).isNull()
    }

    @Test
    fun restartForPractice() {
        val quizzes = KarutaQuizzes(
            listOf(
                createAnsweredQuiz(1, Date(), 3000, ChoiceNo.FIRST, true),
                createAnsweredQuiz(2, Date(), 3000, ChoiceNo.SECOND, false)
            )
        )
        val karutas = Karutas((0..10).map { createKaruta(it) })

        whenever(karutaRepository.list()).thenReturn(karutas)
        whenever(karutaQuizRepository.list()).thenReturn(quizzes)
        whenever(karutaQuizRepository.initialize(any())).thenAnswer { }

        val actual = actionCreator.restartForPractice()

        assertThat(actual.karutaQuizId).isNotNull
        assertThat(actual.error).isNull()
    }

    @Test
    fun startWithEmpty() {
        val fromId = KarutaIdentifier(1)
        val toId = KarutaIdentifier(10)
        val karutas = Karutas((0..10).map { createKaruta(it) })
        whenever(karutaRepository.findIds(fromId, toId, null, null)).thenReturn(KarutaIds(listOf()))
        whenever(karutaRepository.list()).thenReturn(karutas)
        whenever(karutaQuizRepository.initialize(any())).thenAnswer { }

        val actual = actionCreator.start(fromId, toId, null, null)

        assertThat(actual.karutaQuizId).isNull()
        assertThat(actual.error).isNull()
    }

    @Test
    fun fetchNext() {
        val quiz = createQuiz(1)

        whenever(karutaQuizRepository.first()).thenReturn(quiz)

        val actual = actionCreator.fetchNext()

        assertThat(actual.karutaQuizId).isEqualTo(quiz.identifier)
        assertThat(actual.error).isNull()
    }

    @Test
    fun fetchNextWhenNotFound() {
        whenever(karutaQuizRepository.first()).thenReturn(null)

        val actual = actionCreator.fetchNext()

        assertThat(actual).isInstanceOf(OpenNextQuizAction::class.java)
        assertThat(actual.error).isNotNull()
    }

    @Test
    fun aggregateResults() {
        val quizzes = KarutaQuizzes(
            listOf(
                createAnsweredQuiz(1, Date(), 3000, ChoiceNo.FIRST, true),
                createAnsweredQuiz(2, Date(), 3000, ChoiceNo.SECOND, false)
            )
        )
        whenever(karutaQuizRepository.list()).thenReturn(quizzes)

        val actual = actionCreator.aggregateResults()

        assertThat(actual.trainingResult).isNotNull
        assertThat(actual.error).isNull()
    }

    @Test
    fun aggregateResultsExistsNotAnswered() {
        val quizzes = KarutaQuizzes(
            listOf(
                createAnsweredQuiz(1, Date(), 3000, ChoiceNo.FIRST, true),
                createQuiz(2)
            )
        )
        whenever(karutaQuizRepository.list()).thenReturn(quizzes)

        val actual = actionCreator.aggregateResults()

        assertThat(actual).isInstanceOf(AggregateResultsAction::class.java)
        assertThat(actual.error).isNotNull()
    }
}
