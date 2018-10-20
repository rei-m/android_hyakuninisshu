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
package me.rei_m.hyakuninisshu.presentation.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.quiz.AnswerQuizAction
import me.rei_m.hyakuninisshu.action.quiz.FetchQuizAction
import me.rei_m.hyakuninisshu.action.quiz.StartQuizAction
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.presentation.Store
import me.rei_m.hyakuninisshu.util.Event
import javax.inject.Inject

class QuizStore(dispatcher: Dispatcher) : Store() {

    private val _karutaQuizContent = MutableLiveData<KarutaQuizContent>()
    val karutaQuizContent: LiveData<KarutaQuizContent> = _karutaQuizContent

    private val _unhandledErrorEvent = MutableLiveData<Event<Unit>>()
    val unhandledErrorEvent: LiveData<Event<Unit>> = _unhandledErrorEvent

    init {
        register(dispatcher.on(FetchQuizAction::class.java).subscribe {
            if (it.isSucceeded) {
                _karutaQuizContent.value = it.karutaQuizContent
            } else {
                _unhandledErrorEvent.value = Event(Unit)
            }
        }, dispatcher.on(StartQuizAction::class.java).subscribe {
            if (it.isSucceeded) {
                _karutaQuizContent.value = it.karutaQuizContent
            } else {
                _unhandledErrorEvent.value = Event(Unit)
            }
        }, dispatcher.on(AnswerQuizAction::class.java).subscribe {
            if (it.isSucceeded) {
                _karutaQuizContent.value = it.karutaQuizContent
            } else {
                _unhandledErrorEvent.value = Event(Unit)
            }
        })
    }

    class Factory @Inject constructor(private val dispatcher: Dispatcher) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuizStore(dispatcher) as T
        }
    }
}
