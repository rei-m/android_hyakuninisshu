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

package me.rei_m.hyakuninisshu.action.training

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.*
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import me.rei_m.hyakuninisshu.domain.util.rx.TestSchedulerProvider
import me.rei_m.hyakuninisshu.helper.TestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class TrainingActionDispatcherTest : TestHelper {

    private lateinit var actionDispatcher: TrainingActionDispatcher

    private lateinit var karutaRepository: KarutaRepository
    private lateinit var karutaQuizRepository: KarutaQuizRepository
    private lateinit var karutaExamRepository: KarutaExamRepository

    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = mock {}
        karutaRepository = mock {}
        karutaQuizRepository = mock {}
        karutaExamRepository = mock {}
        actionDispatcher = TrainingActionDispatcher(
                karutaRepository,
                karutaQuizRepository,
                karutaExamRepository,
                dispatcher,
                TestSchedulerProvider()
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
                .thenReturn(Single.just(KarutaIds(listOf(KarutaIdentifier(1)))))
        whenever(karutaRepository.list()).thenReturn(Single.just(karutas))
        whenever(karutaQuizRepository.initialize(any())).thenReturn(Completable.complete())

        actionDispatcher.start(fromId, toId, kimariji, color)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartTrainingAction::class.java)
            if (it is StartTrainingAction) {
                assertThat(it.karutaQuizId).isNotNull
                assertThat(it.error).isNull()
            }
        })
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

        whenever(karutaExamRepository.list()).thenReturn(Single.just(KarutaExams(listOf(exam))))
        whenever(karutaRepository.list()).thenReturn(Single.just(karutas))
        whenever(karutaQuizRepository.initialize(any())).thenReturn(Completable.complete())

        actionDispatcher.startForExam()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartTrainingAction::class.java)
            if (it is StartTrainingAction) {
                assertThat(it.karutaQuizId).isNotNull
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun restartForPractice() {
        val quizzes = KarutaQuizzes(listOf(
                createAnsweredQuiz(1, Date(), 3000, ChoiceNo.FIRST, true),
                createAnsweredQuiz(2, Date(), 3000, ChoiceNo.SECOND, false)
        ))
        val karutas = Karutas((0..10).map { createKaruta(it) })

        whenever(karutaRepository.list()).thenReturn(Single.just(karutas))
        whenever(karutaQuizRepository.list()).thenReturn(Single.just(quizzes))
        whenever(karutaQuizRepository.initialize(any())).thenReturn(Completable.complete())

        actionDispatcher.restartForPractice()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartTrainingAction::class.java)
            if (it is StartTrainingAction) {
                assertThat(it.karutaQuizId).isNotNull
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun startWithEmpty() {
        val fromId = KarutaIdentifier(1)
        val toId = KarutaIdentifier(10)
        val karutas = Karutas((0..10).map { createKaruta(it) })
        whenever(karutaRepository.findIds(fromId, toId, null, null)).thenReturn(Single.just(KarutaIds(listOf())))
        whenever(karutaRepository.list()).thenReturn(Single.just(karutas))
        whenever(karutaQuizRepository.initialize(any())).thenReturn(Completable.complete())

        actionDispatcher.start(fromId, toId, null, null)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartTrainingAction::class.java)
            if (it is StartTrainingAction) {
                assertThat(it.karutaQuizId).isNull()
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun startWithError() {
        val fromId = KarutaIdentifier(1)
        val toId = KarutaIdentifier(10)
        whenever(karutaRepository.findIds(fromId, toId, null, null)).thenReturn(Single.error(RuntimeException()))
        whenever(karutaRepository.list()).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.start(fromId, toId, null, null)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartTrainingAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun fetchNext() {
        val quiz = createQuiz(1)

        whenever(karutaQuizRepository.first()).thenReturn(Single.just(quiz))

        actionDispatcher.fetchNext()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(OpenNextQuizAction::class.java)
            if (it is OpenNextQuizAction) {
                assertThat(it.karutaQuizId).isEqualTo(quiz.identifier())
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchNextWithError() {
        whenever(karutaQuizRepository.first()).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.fetchNext()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(OpenNextQuizAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun aggregateResults() {
        val quizzes = KarutaQuizzes(listOf(
                createAnsweredQuiz(1, Date(), 3000, ChoiceNo.FIRST, true),
                createAnsweredQuiz(2, Date(), 3000, ChoiceNo.SECOND, false)
        ))
        whenever(karutaQuizRepository.list()).thenReturn(Single.just(quizzes))

        actionDispatcher.aggregateResults()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(AggregateResultsAction::class.java)
            if (it is AggregateResultsAction) {
                assertThat(it.trainingResult).isNotNull
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun aggregateResultsWithError() {
        whenever(karutaQuizRepository.list()).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.aggregateResults()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(AggregateResultsAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }
}
