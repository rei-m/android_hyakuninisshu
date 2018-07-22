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

package me.rei_m.hyakuninisshu.presentation.core

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.quiz.AnswerQuizAction
import me.rei_m.hyakuninisshu.action.quiz.FetchQuizAction
import me.rei_m.hyakuninisshu.action.quiz.StartQuizAction
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.presentation.Store
import me.rei_m.hyakuninisshu.presentation.helper.SingleLiveEvent
import javax.inject.Inject

class QuizStore(dispatcher: Dispatcher) : Store() {

    private val karutaQuizContentLiveData = MutableLiveData<KarutaQuizContent>()
    val karutaQuizContent: LiveData<KarutaQuizContent> = karutaQuizContentLiveData

    private val unhandledErrorEventLiveData: SingleLiveEvent<Void> = SingleLiveEvent()
    val unhandledErrorEvent: LiveData<Void> = unhandledErrorEventLiveData

    init {
        register(dispatcher.on(FetchQuizAction::class.java).subscribe {
            if (it.error == null) {
                karutaQuizContentLiveData.value = it.karutaQuizContent
            } else {
                unhandledErrorEventLiveData.call()
            }
        }, dispatcher.on(StartQuizAction::class.java).subscribe {
            if (it.error == null) {
                karutaQuizContentLiveData.value = it.karutaQuizContent
            } else {
                unhandledErrorEventLiveData.call()
            }
        }, dispatcher.on(AnswerQuizAction::class.java).subscribe {
            if (it.error == null) {
                karutaQuizContentLiveData.value = it.karutaQuizContent
            } else {
                unhandledErrorEventLiveData.call()
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
