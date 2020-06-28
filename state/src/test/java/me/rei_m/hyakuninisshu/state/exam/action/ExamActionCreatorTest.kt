/*
 * Copyright (c) 2020. Rei Matsushita
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

package me.rei_m.hyakuninisshu.state.exam.action

import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import me.rei_m.hyakuninisshu.domain.question.model.ExamCollection
import me.rei_m.hyakuninisshu.domain.question.model.ExamId
import me.rei_m.hyakuninisshu.domain.question.model.ExamRepository
import me.rei_m.hyakuninisshu.domain.question.model.QuestionCollection
import me.rei_m.hyakuninisshu.domain.question.model.QuestionRepository
import me.rei_m.hyakuninisshu.domain.question.service.CreateQuestionListService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ExamActionCreatorTest : TestHelper {
    private lateinit var actionCreator: ExamActionCreator
    private lateinit var karutaRepository: KarutaRepository
    private lateinit var questionRepository: QuestionRepository
    private lateinit var examRepository: ExamRepository
    private lateinit var createQuestionListService: CreateQuestionListService

    @Before
    fun setUp() {
        karutaRepository = mock {}
        questionRepository = mock {}
        examRepository = mock {}
        createQuestionListService = CreateQuestionListService()
        actionCreator = ExamActionCreator(
            context = ApplicationProvider.getApplicationContext(),
            karutaRepository = karutaRepository,
            questionRepository = questionRepository,
            examRepository = examRepository,
            createQuestionListService = createQuestionListService
        )
    }

    @Test
    fun startExam_success() = runBlocking {
        whenever(karutaRepository.findAll()).thenReturn(createAllKarutaList())
        whenever(questionRepository.initialize(any())).thenAnswer { }

        val actual = actionCreator.start()
        assertThat(actual).isInstanceOf(StartExamAction.Success::class.java)
        return@runBlocking
    }

    @Test
    fun startExam_failure() = runBlocking {
        whenever(karutaRepository.findAll()).thenReturn(createAllKarutaList())
        whenever(questionRepository.initialize(any())).thenThrow(IllegalArgumentException())

        val actual = actionCreator.start()
        assertThat(actual).isInstanceOf(StartExamAction.Failure::class.java)
        return@runBlocking
    }

    @Test
    fun finish_success() = runBlocking {
        val question1 = createQuestionAnswered()
        val question2 = createQuestionAnswered()

        val createdExamId = ExamId(1)

        whenever(questionRepository.findCollection()).thenReturn(
            QuestionCollection(
                listOf(question1, question2)
            )
        )
        whenever(examRepository.add(any(), any())).thenReturn(createdExamId)
        whenever(examRepository.findCollection()).thenReturn(ExamCollection(listOf(createExam())))
        whenever(examRepository.deleteList(any())).thenAnswer { }

        val actual = actionCreator.finish()
        assertThat(actual).isInstanceOf(FinishExamAction.Success::class.java)

        return@runBlocking
    }

    @Test
    fun finish_failure() = runBlocking {
        val question1 = createQuestionAnswered()
        val question2 = createQuestionInAnswer()

        val createdExamId = ExamId(1)

        whenever(questionRepository.findCollection()).thenReturn(
            QuestionCollection(
                listOf(question1, question2)
            )
        )
        whenever(examRepository.add(any(), any())).thenReturn(createdExamId)
        whenever(examRepository.findCollection()).thenReturn(ExamCollection(listOf(createExam())))
        whenever(examRepository.deleteList(any())).thenAnswer { }

        val actual = actionCreator.finish()
        assertThat(actual).isInstanceOf(FinishExamAction.Failure::class.java)

        return@runBlocking
    }

    @Test
    fun fetchRecentResult_success() = runBlocking {
        whenever(examRepository.last()).thenReturn(createExam())

        val actual = actionCreator.fetchRecentResult()
        assertThat(actual).isInstanceOf(FetchRecentExamResultAction.Success::class.java)

        return@runBlocking
    }

    @Test
    fun fetchRecentResult_successWithNull() = runBlocking {
        whenever(examRepository.last()).thenReturn(null)

        val actual = actionCreator.fetchRecentResult()
        assertThat(actual).isInstanceOf(FetchRecentExamResultAction.Success::class.java)

        return@runBlocking
    }

    @Test
    fun fetchRecentResult_failure() = runBlocking {
        whenever(examRepository.last()).thenThrow(RuntimeException())

        val actual = actionCreator.fetchRecentResult()
        assertThat(actual).isInstanceOf(FetchRecentExamResultAction.Failure::class.java)

        return@runBlocking
    }

    @Test
    fun fetchResult_success() = runBlocking {
        val examId = ExamId(1)
        whenever(examRepository.findById(examId)).thenReturn(createExam())
        whenever(karutaRepository.findAll()).thenReturn(createAllKarutaList())

        val actual = actionCreator.fetchResult(examId.value)
        assertThat(actual).isInstanceOf(FetchExamResultAction.Success::class.java)

        return@runBlocking
    }

    @Test
    fun fetchResult_failure() = runBlocking {
        val examId = ExamId(1)
        whenever(examRepository.findById(examId)).thenThrow(RuntimeException())
        whenever(karutaRepository.findAll()).thenReturn(createAllKarutaList())

        val actual = actionCreator.fetchResult(examId.value)
        assertThat(actual).isInstanceOf(FetchExamResultAction.Failure::class.java)

        return@runBlocking
    }
}
