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
package me.rei_m.hyakuninisshu.action.exam

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.InternalCoroutinesApi
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExams
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzesResultSummary
import me.rei_m.hyakuninisshu.helper.TestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class ExamActionCreatorTest : TestHelper {

    private lateinit var actionCreator: ExamActionCreator

    private lateinit var karutaRepository: KarutaRepository
    private lateinit var karutaQuizRepository: KarutaQuizRepository
    private lateinit var karutaExamRepository: KarutaExamRepository

    private lateinit var dispatcher: Dispatcher

    @InternalCoroutinesApi
    @Before
    fun setUp() {
        dispatcher = mock {}
        karutaRepository = mock {}
        karutaQuizRepository = mock {}
        karutaExamRepository = mock {}
        actionCreator = ExamActionCreator(
            karutaRepository,
            karutaQuizRepository,
            karutaExamRepository,
            dispatcher
        )
    }

    @Test
    fun fetch() {
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
                wrongKarutaIds = KarutaIds(listOf())
            )
        )

        whenever(karutaExamRepository.findBy(examId)).thenReturn(exam)

        actionCreator.fetch(examId)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchExamAction::class.java)
            if (it is FetchExamAction) {
                assertThat(it.karutaExam).isEqualTo(exam)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchWhenNotFound() {
        val examId = KarutaExamIdentifier(1)
        whenever(karutaExamRepository.findBy(examId)).thenReturn(null)

        actionCreator.fetch(examId)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchExamAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun fetchRecent() {
        whenever(karutaExamRepository.list()).thenReturn(KarutaExams(listOf()))

        actionCreator.fetchRecent()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchRecentExamAction::class.java)
            if (it is FetchRecentExamAction) {
                assertThat(it.karutaExam).isNull()
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchAll() {
        whenever(karutaExamRepository.list()).thenReturn(KarutaExams(listOf()))

        actionCreator.fetchAll()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchAllExamAction::class.java)
            if (it is FetchAllExamAction) {
                assertThat(it.karutaExamList).isNotNull
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun start() {
        val karutaList: List<Karuta> = listOf(
            createKaruta(id = 1),
            createKaruta(id = 2),
            createKaruta(id = 3),
            createKaruta(id = 4),
            createKaruta(id = 5)
        )

        whenever(karutaRepository.list()).thenReturn(Karutas(karutaList))
        whenever(karutaRepository.findIds()).thenReturn(KarutaIds(listOf(karutaList.first().identifier)))
        whenever(karutaQuizRepository.initialize(any())).thenAnswer { }

        actionCreator.start()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartExamAction::class.java)
            if (it is StartExamAction) {
                assertThat(it.karutaQuizId).isNotNull
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchNext() {
        val quiz = createQuiz(1)

        whenever(karutaQuizRepository.first()).thenReturn(quiz)

        actionCreator.fetchNext()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(OpenNextQuizAction::class.java)
            if (it is OpenNextQuizAction) {
                assertThat(it.karutaQuizId).isEqualTo(quiz.identifier)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchNextWhenNotFound() {
        whenever(karutaQuizRepository.first()).thenReturn(null)

        actionCreator.fetchNext()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(OpenNextQuizAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun finish() {
        val quiz = createAnsweredQuiz(1, Date(), 500, ChoiceNo.FIRST, true)
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
                wrongKarutaIds = KarutaIds(listOf())
            )
        )
        whenever(karutaQuizRepository.list()).thenReturn(KarutaQuizzes(listOf(quiz)))
        whenever(karutaExamRepository.storeResult(any(), any())).thenReturn(examId)
        whenever(karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT)).thenAnswer { }
        whenever(karutaExamRepository.findBy(examId)).thenReturn(exam)

        actionCreator.finish()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FinishExamAction::class.java)
            if (it is FinishExamAction) {
                assertThat(it.karutaExam).isEqualTo(exam)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun finishWhenExistNotAnswered() {
        val quiz = createQuiz(1)

        whenever(karutaQuizRepository.list()).thenReturn(KarutaQuizzes(listOf(quiz)))

        actionCreator.finish()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FinishExamAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }
}
