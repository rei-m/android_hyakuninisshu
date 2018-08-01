package me.rei_m.hyakuninisshu.action.quiz

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Single
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizCounter
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import me.rei_m.hyakuninisshu.domain.util.rx.TestSchedulerProvider
import me.rei_m.hyakuninisshu.helper.TestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class QuizActionDispatcherTest : TestHelper {

    private lateinit var actionDispatcher: QuizActionDispatcher

    private lateinit var karutaRepository: KarutaRepository
    private lateinit var karutaQuizRepository: KarutaQuizRepository

    private lateinit var dispatcher: Dispatcher

    @Before
    fun setUp() {
        dispatcher = mock {}
        karutaRepository = mock {}
        karutaQuizRepository = mock { }
        actionDispatcher = QuizActionDispatcher(
                karutaRepository,
                karutaQuizRepository,
                dispatcher,
                TestSchedulerProvider()
        )
    }

    @Test
    fun fetch() {
        val quiz = createQuiz(1)
        whenever(karutaQuizRepository.findBy(quiz.identifier())).thenReturn(Single.just(quiz))
        whenever(karutaQuizRepository.countQuizByAnswered()).thenReturn(Single.just(KarutaQuizCounter(10, 1)))
        quiz.choiceList.forEach {
            val karuta = createKaruta(id = it.value)
            whenever(karutaRepository.findBy(it)).thenReturn(Single.just(karuta))
        }

        actionDispatcher.fetch(quiz.identifier())

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchQuizAction::class.java)
            if (it is FetchQuizAction) {
                assertThat(it.karutaQuizContent?.quiz).isEqualTo(quiz)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun fetchWithTest() {
        val quizId = KarutaQuizIdentifier()
        whenever(karutaQuizRepository.findBy(quizId)).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.fetch(quizId)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(FetchQuizAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun start() {
        val quiz = createQuiz(1)
        val startDate = Date()
        whenever(karutaQuizRepository.findBy(quiz.identifier())).thenReturn(Single.just(quiz))
        whenever(karutaQuizRepository.countQuizByAnswered()).thenReturn(Single.just(KarutaQuizCounter(10, 1)))
        quiz.choiceList.forEach {
            val karuta = createKaruta(id = it.value)
            whenever(karutaRepository.findBy(it)).thenReturn(Single.just(karuta))
        }
        whenever(karutaQuizRepository.store(any())).thenReturn(Completable.complete())

        actionDispatcher.start(quiz.identifier(), startDate)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartQuizAction::class.java)
            if (it is StartQuizAction) {
                assertThat(it.karutaQuizContent?.quiz?.startDate).isEqualTo(startDate)
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun startWithError() {
        val quiz = createQuiz(1)
        val startDate = Date()
        whenever(karutaQuizRepository.findBy(quiz.identifier())).thenReturn(Single.just(quiz))
        whenever(karutaQuizRepository.countQuizByAnswered()).thenReturn(Single.just(KarutaQuizCounter(10, 1)))
        quiz.choiceList.forEach {
            val karuta = createKaruta(id = it.value)
            whenever(karutaRepository.findBy(it)).thenReturn(Single.just(karuta))
        }
        whenever(karutaQuizRepository.store(any())).thenReturn(Completable.error(RuntimeException()))

        actionDispatcher.start(quiz.identifier(), startDate)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(StartQuizAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }

    @Test
    fun answer() {
        val quiz = createStartedQuiz(1, Date())
        val answerDate = Date()
        whenever(karutaQuizRepository.findBy(quiz.identifier())).thenReturn(Single.just(quiz))
        whenever(karutaQuizRepository.countQuizByAnswered()).thenReturn(Single.just(KarutaQuizCounter(10, 1)))
        quiz.choiceList.forEach {
            val karuta = createKaruta(id = it.value)
            whenever(karutaRepository.findBy(it)).thenReturn(Single.just(karuta))
        }
        whenever(karutaQuizRepository.store(any())).thenReturn(Completable.complete())

        actionDispatcher.answer(quiz.identifier(), ChoiceNo.FIRST, answerDate)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(AnswerQuizAction::class.java)
            if (it is AnswerQuizAction) {
                assertThat(it.karutaQuizContent?.quiz?.result).isNotNull
                assertThat(it.error).isNull()
            }
        })
    }

    @Test
    fun answerWithError() {
        val quiz = createStartedQuiz(1, Date())
        val answerDate = Date()
        whenever(karutaQuizRepository.findBy(quiz.identifier())).thenReturn(Single.error(RuntimeException()))

        actionDispatcher.answer(quiz.identifier(), ChoiceNo.FIRST, answerDate)

        verify(dispatcher).dispatch(check {
            assertThat(it).isInstanceOf(AnswerQuizAction::class.java)
            assertThat(it.error).isNotNull()
        })
    }
}
