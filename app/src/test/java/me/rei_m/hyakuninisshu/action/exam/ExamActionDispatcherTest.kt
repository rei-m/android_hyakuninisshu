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

package me.rei_m.hyakuninisshu.action.exam

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.*
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import me.rei_m.hyakuninisshu.domain.util.rx.TestSchedulerProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ExamActionDispatcherTest {

    private lateinit var actionDispatcher: ExamActionDispatcher

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
        actionDispatcher = ExamActionDispatcher(
                karutaRepository,
                karutaQuizRepository,
                karutaExamRepository,
                dispatcher,
                TestSchedulerProvider()
        )
    }

    @Test
    fun fetch() {
        val examId = KarutaExamIdentifier(1)
        val exam = KarutaExam(
                identifier = examId,
                result = KarutaExamResult(
                        resultSummary = KarutaQuizzesResultSummary(
                                quizCount = 100,
                                correctCount = 100,
                                averageAnswerSec = 5f
                        ),
                        wrongKarutaIds = KarutaIds(listOf())
                )
        )

        whenever(karutaExamRepository.findBy(examId)).thenReturn(Single.just(exam))

        actionDispatcher.fetch(examId)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchExamAction::class.java)
            if (it is FetchExamAction) {
                assertThat(it.karutaExam).isEqualTo(exam)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchWithError() {
        val examId = KarutaExamIdentifier(1)
        whenever(karutaExamRepository.findBy(examId)).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.fetch(examId)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchExamAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun fetchRecent() {
        whenever(karutaExamRepository.list()).thenReturn(Single.just(KarutaExams(listOf())))

        actionDispatcher.fetchRecent()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchRecentExamAction::class.java)
            if (it is FetchRecentExamAction) {
                assertThat(it.karutaExam).isNull()
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchRecentWithError() {
        whenever(karutaExamRepository.list()).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.fetchRecent()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchRecentExamAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun start() {
        val karutaList: List<Karuta> = listOf(
                createKaruta(1),
                createKaruta(2),
                createKaruta(3),
                createKaruta(4),
                createKaruta(5)
        )

        whenever(karutaRepository.list()).thenReturn(Single.just(Karutas(karutaList)))
        whenever(karutaRepository.findIds()).thenReturn(Single.just(KarutaIds(listOf(karutaList.first().identifier()))))
        whenever(karutaQuizRepository.initialize(any())).thenReturn(Completable.complete())

        actionDispatcher.start()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartExamAction::class.java)
            if (it is StartExamAction) {
                assertThat(it.karutaQuizId).isNotNull
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun startWithError() {
        whenever(karutaRepository.list()).thenReturn(Single.error(RuntimeException()))
        whenever(karutaRepository.findIds()).thenReturn(Single.just(KarutaIds(listOf())))
        whenever(karutaQuizRepository.initialize(any())).thenReturn(Completable.complete())

        actionDispatcher.start()

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartExamAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    private fun createKaruta(id: Int): Karuta {
        val identifier = KarutaIdentifier(id)
        val creator = "creator"
        val kamiNoKu = KamiNoKu(
                KamiNoKuIdentifier(identifier.value),
                Phrase("しょく_$id", "初句_$id"),
                Phrase("にく_$id", "二句_$id"),
                Phrase("さんく_$id", "三句_$id")
        )
        val shimoNoKu = ShimoNoKu(
                ShimoNoKuIdentifier(identifier.value),
                Phrase("よんく_$id", "四句_$id"),
                Phrase("ごく_$id", "五句_$id")
        )
        val imageNo = ImageNo("001")
        val translation = "歌の訳"
        return Karuta(identifier, creator, kamiNoKu, shimoNoKu, Kimariji.ONE, imageNo, translation, Color.BLUE)
    }
}
