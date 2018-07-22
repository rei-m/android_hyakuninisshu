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

package me.rei_m.hyakuninisshu.presentation.exam

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.exam.FetchExamAction
import me.rei_m.hyakuninisshu.action.exam.FinishExamAction
import me.rei_m.hyakuninisshu.action.exam.OpenNextQuizAction
import me.rei_m.hyakuninisshu.action.exam.StartExamAction
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.presentation.Store
import me.rei_m.hyakuninisshu.presentation.helper.SingleLiveEvent
import javax.inject.Inject

class ExamStore(dispatcher: Dispatcher) : Store() {

    private val currentKarutaQuizIdLiveData = MutableLiveData<KarutaQuizIdentifier?>()
    val currentKarutaQuizId: LiveData<KarutaQuizIdentifier?> = currentKarutaQuizIdLiveData

    private val resultLiveData = MutableLiveData<KarutaExam?>()
    val result: LiveData<KarutaExam?> = resultLiveData

    private val notFoundQuizEventLiveData = SingleLiveEvent<Void>()
    val notFoundQuizEvent = notFoundQuizEventLiveData

    private val notFoundExamEventLiveData = SingleLiveEvent<Void>()
    val notFoundExamEvent = notFoundExamEventLiveData

    init {
        currentKarutaQuizIdLiveData.value = null
        resultLiveData.value = null

        register(dispatcher.on(StartExamAction::class.java).subscribe {
            if (it.error) {
                notFoundQuizEventLiveData.call()
            } else {
                currentKarutaQuizIdLiveData.value = it.karutaQuizId
            }
        }, dispatcher.on(OpenNextQuizAction::class.java).subscribe {
            if (it.error) {
                notFoundQuizEventLiveData.call()
            } else {
                currentKarutaQuizIdLiveData.value = it.karutaQuizId
            }
        }, dispatcher.on(FinishExamAction::class.java).subscribe {
            currentKarutaQuizIdLiveData.value = null
            if (it.error) {
                notFoundExamEventLiveData.call()
            } else {
                resultLiveData.value = it.karutaExam
            }
        }, dispatcher.on(FetchExamAction::class.java).subscribe {
            if (it.error) {
                notFoundExamEventLiveData.call()
            } else {
                resultLiveData.value = it.karutaExam
            }
        })
    }

    class Factory @Inject constructor(private val dispatcher: Dispatcher) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamStore(dispatcher) as T
        }
    }
}
