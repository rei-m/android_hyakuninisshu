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

package me.rei_m.hyakuninisshu.state.training.action

import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaColor
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import me.rei_m.hyakuninisshu.domain.karuta.model.Kimariji
import me.rei_m.hyakuninisshu.domain.question.model.ExamCollection
import me.rei_m.hyakuninisshu.domain.question.model.ExamRepository
import me.rei_m.hyakuninisshu.domain.question.model.QuestionCollection
import me.rei_m.hyakuninisshu.domain.question.model.QuestionRepository
import me.rei_m.hyakuninisshu.domain.question.service.CreateQuestionListService
import me.rei_m.hyakuninisshu.state.training.model.ColorCondition
import me.rei_m.hyakuninisshu.state.training.model.KimarijiCondition
import me.rei_m.hyakuninisshu.state.training.model.RangeFromCondition
import me.rei_m.hyakuninisshu.state.training.model.RangeToCondition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TrainingActionCreatorTest : TestHelper {
    private lateinit var actionCreator: TrainingActionCreator
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
        actionCreator =
            TrainingActionCreator(
                context = ApplicationProvider.getApplicationContext(),
                karutaRepository = karutaRepository,
                questionRepository = questionRepository,
                examRepository = examRepository,
                createQuestionListService = createQuestionListService,
            )
    }

    @Test
    fun start_success() =
        runBlocking {
            whenever(
                karutaRepository.findAllWithCondition(
                    fromNo = KarutaNo(11),
                    toNo = KarutaNo(80),
                    kimarijis = listOf(Kimariji.TWO),
                    colors = listOf(KarutaColor.BLUE),
                ),
            ).thenReturn(createAllKarutaList())
            whenever(questionRepository.initialize(any())).thenAnswer { }

            val actual =
                actionCreator.start(
                    fromCondition = RangeFromCondition.ELEVEN,
                    toCondition = RangeToCondition.EIGHTY,
                    kimariji = KimarijiCondition.TWO,
                    color = ColorCondition.BLUE,
                )
            assertThat(actual).isInstanceOf(StartTrainingAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun start_success_empty() =
        runBlocking {
            whenever(
                karutaRepository.findAllWithCondition(
                    fromNo = KarutaNo(11),
                    toNo = KarutaNo(80),
                    kimarijis = listOf(Kimariji.TWO),
                    colors = listOf(KarutaColor.BLUE),
                ),
            ).thenReturn(listOf())
            whenever(questionRepository.initialize(any())).thenAnswer { }

            val actual =
                actionCreator.start(
                    fromCondition = RangeFromCondition.ELEVEN,
                    toCondition = RangeToCondition.EIGHTY,
                    kimariji = KimarijiCondition.TWO,
                    color = ColorCondition.BLUE,
                )
            assertThat(actual).isInstanceOf(StartTrainingAction.Empty::class.java)
            return@runBlocking
        }

    @Test
    fun start_failure() =
        runBlocking {
            whenever(
                karutaRepository.findAllWithCondition(
                    fromNo = KarutaNo(11),
                    toNo = KarutaNo(80),
                    kimarijis = listOf(Kimariji.TWO),
                    colors = listOf(KarutaColor.BLUE),
                ),
            ).thenThrow(RuntimeException())
            whenever(questionRepository.initialize(any())).thenAnswer { }

            val actual =
                actionCreator.start(
                    fromCondition = RangeFromCondition.ELEVEN,
                    toCondition = RangeToCondition.EIGHTY,
                    kimariji = KimarijiCondition.TWO,
                    color = ColorCondition.BLUE,
                )
            assertThat(actual).isInstanceOf(StartTrainingAction.Failure::class.java)
            return@runBlocking
        }

    @Test
    fun start_startByExamPractice_success() =
        runBlocking {
            val wrongKarutaNoCollection = KarutaNoCollection(listOf(KarutaNo(1), KarutaNo(2)))
            val examCollection =
                ExamCollection(
                    listOf(
                        createExam(
                            wrongKarutaNoCollection = wrongKarutaNoCollection,
                        ),
                    ),
                )
            whenever(examRepository.findCollection()).thenReturn(examCollection)
            whenever(
                karutaRepository.findAllWithNo(wrongKarutaNoCollection.values),
            ).thenReturn(createAllKarutaList())
            whenever(questionRepository.initialize(any())).thenAnswer { }

            val actual = actionCreator.startByExamPractice()
            assertThat(actual).isInstanceOf(StartTrainingAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun start_startByExamPractice_success_empty() =
        runBlocking {
            val examCollection = ExamCollection(listOf())
            whenever(examRepository.findCollection()).thenReturn(examCollection)
            val actual = actionCreator.startByExamPractice()
            assertThat(actual).isInstanceOf(StartTrainingAction.Empty::class.java)
            return@runBlocking
        }

    @Test
    fun start_startByExamPractice_failure() =
        runBlocking {
            whenever(examRepository.findCollection()).thenThrow(RuntimeException())
            val actual = actionCreator.startByExamPractice()
            assertThat(actual).isInstanceOf(StartTrainingAction.Failure::class.java)
            return@runBlocking
        }

    @Test
    fun start_restart_success() =
        runBlocking {
            val questionCollection =
                QuestionCollection(
                    listOf(
                        createQuestionAnswered(
                            correctNo = KarutaNo(1),
                            isCorrect = false,
                        ),
                        createQuestionAnswered(
                            correctNo = KarutaNo(2),
                            isCorrect = true,
                        ),
                    ),
                )
            whenever(questionRepository.findCollection()).thenReturn(questionCollection)
            whenever(karutaRepository.findAllWithNo(listOf(KarutaNo(1)))).thenReturn(createAllKarutaList())
            whenever(questionRepository.initialize(any())).thenAnswer { }

            val actual = actionCreator.restart()
            assertThat(actual).isInstanceOf(StartTrainingAction.Success::class.java)
            return@runBlocking
        }

    @Test
    fun start_restart_success_empty() =
        runBlocking {
            val questionCollection = QuestionCollection(listOf())
            whenever(questionRepository.findCollection()).thenReturn(questionCollection)
            val actual = actionCreator.restart()
            assertThat(actual).isInstanceOf(StartTrainingAction.Empty::class.java)
            return@runBlocking
        }

    @Test
    fun start_restart_failure() =
        runBlocking {
            whenever(questionRepository.findCollection()).thenThrow(RuntimeException())
            val actual = actionCreator.restart()
            assertThat(actual).isInstanceOf(StartTrainingAction.Failure::class.java)
            return@runBlocking
        }
}
