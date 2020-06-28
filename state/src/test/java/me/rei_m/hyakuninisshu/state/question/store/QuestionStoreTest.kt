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

package me.rei_m.hyakuninisshu.state.question.store

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.schedulers.Schedulers
import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.state.core.ActionDispatcher
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.material.model.Material
import me.rei_m.hyakuninisshu.state.question.action.AnswerQuestionAction
import me.rei_m.hyakuninisshu.state.question.action.StartAnswerQuestionAction
import me.rei_m.hyakuninisshu.state.question.action.StartQuestionAction
import me.rei_m.hyakuninisshu.state.question.model.Question
import me.rei_m.hyakuninisshu.state.question.model.QuestionState
import me.rei_m.hyakuninisshu.state.question.model.YomiFuda
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuestionStoreTest : TestHelper {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dispatcher: Dispatcher
    private lateinit var store: QuestionStore

    private val question = Question(
        id = "1",
        no = 1,
        position = "1/10",
        toriFudaList = listOf(),
        yomiFuda = YomiFuda(
            karutaNo = 1,
            firstLine = "aaa",
            secondLine = "bbb",
            thirdLine = "ccc"
        )
    )

    @Before
    fun setUp() {
        dispatcher = ActionDispatcher(Schedulers.trampoline())
        store = QuestionStore(dispatcher)
    }

    @After
    fun tearDown() {
        store.dispose()
    }

    @Test
    fun initialState() {
        assertThat(store.question.value).isNull()
        assertThat(store.state.value).isNull()
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedStartQuestionAction() {
        val state = QuestionState.Ready
        dispatcher.dispatch(StartQuestionAction.Success(question, state))
        assertThat(store.question.value).isEqualTo(question)
        assertThat(store.state.value).isEqualTo(state)
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedFailedStartQuestionAction() {
        dispatcher.dispatch(StartQuestionAction.Failure(RuntimeException()))
        assertThat(store.question.value).isNull()
        assertThat(store.state.value).isNull()
        assertThat(store.isFailure.value).isTrue()
    }

    @Test
    fun state_receivedStartAnswerQuestionAction() {
        val state = QuestionState.InAnswer
        dispatcher.dispatch(StartAnswerQuestionAction.Success(state))
        assertThat(store.state.value).isEqualTo(state)
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedFailedStartAnswerQuestionAction() {
        dispatcher.dispatch(StartAnswerQuestionAction.Failure(RuntimeException()))
        assertThat(store.question.value).isNull()
        assertThat(store.state.value).isNull()
        assertThat(store.isFailure.value).isTrue()
    }

    @Test
    fun state_receivedAnswerQuestionAction() {
        val state = QuestionState.Answered(
            1,
            true,
            Material(
                no = 1,
                noTxt = "1番",
                kimariji = 1,
                kimarijiTxt = "1字",
                creator = "creator",
                shokuKanji = "秋の田の",
                shokuKana = "あきのたの",
                nikuKanji = "かりほの庵の",
                nikuKana = "かりほのいほの",
                sankuKanji = "苫をあらみ",
                sankuKana = "とまをあらみ",
                shikuKanji = "わが衣手は",
                shikuKana = "わがころもでは",
                gokuKanji = "露にぬれつつ",
                gokuKana = "つゆにぬれつつ",
                translation = "うんたらかんたら",
                imageResId = 1
            ),
            "2"
        )
        dispatcher.dispatch(AnswerQuestionAction.Success(state))
        assertThat(store.state.value).isEqualTo(state)
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedFailedAnswerQuestionAction() {
        dispatcher.dispatch(AnswerQuestionAction.Failure(RuntimeException()))
        assertThat(store.question.value).isNull()
        assertThat(store.state.value).isNull()
        assertThat(store.isFailure.value).isTrue()
    }
}
