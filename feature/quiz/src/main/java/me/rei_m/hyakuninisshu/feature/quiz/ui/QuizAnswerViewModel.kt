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
package me.rei_m.hyakuninisshu.feature.quiz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.quiz.QuizActionCreator
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import kotlin.coroutines.CoroutineContext

class QuizAnswerViewModel(
    mainContext: CoroutineContext,
    ioContext: CoroutineContext,
    dispatcher: Dispatcher,
    actionCreator: QuizActionCreator,
    private val store: QuizStore,
    quizId: KarutaQuizIdentifier
) : AbstractViewModel(mainContext, ioContext, dispatcher) {

    val content: LiveData<KarutaQuizContent> = store.karutaQuizContent

    val existNextQuiz: LiveData<Boolean> = content.map { it.existNext }

    val canGoToResult: LiveData<Boolean> = content.map { !it.existNext }

    val karuta: LiveData<Karuta> = content.map { it.correct }

    val karutaNo: LiveData<Int> = karuta.map { it.identifier.value }

    val kimariji: LiveData<Int> = karuta.map { it.kimariji.value }

    val creator: LiveData<String> = karuta.map { it.creator }

    val firstPhrase: LiveData<String> = karuta.map { it.kamiNoKu.first.kanji.padEnd(5, ' ') }

    val secondPhrase: LiveData<String> = karuta.map { it.kamiNoKu.second.kanji }

    val thirdPhrase: LiveData<String> = karuta.map { it.kamiNoKu.third.kanji }

    val fourthPhrase: LiveData<String> = karuta.map { it.shimoNoKu.fourth.kanji.padEnd(7, ' ') }

    val fifthPhrase: LiveData<String> = karuta.map { it.shimoNoKu.fifth.kanji }

    private val _openNextQuizEvent = MutableLiveData<Event<Unit>>()
    val openNextQuizEvent: LiveData<Event<Unit>> = _openNextQuizEvent

    private val _openResultEvent = MutableLiveData<Event<Unit>>()
    val openResultEvent: LiveData<Event<Unit>> = _openResultEvent

    val unhandledErrorEvent: LiveData<Event<Unit>> = store.unhandledErrorEvent

    init {
        dispatchAction { actionCreator.fetch(quizId) }
    }

    fun onClickNextQuiz() {
        _openNextQuizEvent.value = Event(Unit)
    }

    fun onClickConfirmResult() {
        _openResultEvent.value = Event(Unit)
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory(
        private val mainContext: CoroutineContext,
        private val ioContext: CoroutineContext,
        private val dispatcher: Dispatcher,
        private val actionCreator: QuizActionCreator,
        private val store: QuizStore,
        private val quizId: KarutaQuizIdentifier
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = QuizAnswerViewModel(
            mainContext,
            ioContext,
            dispatcher,
            actionCreator,
            store,
            quizId
        ) as T
    }
}
