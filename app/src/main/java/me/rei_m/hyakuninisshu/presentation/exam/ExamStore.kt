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
import javax.inject.Inject

class ExamStore(dispatcher: Dispatcher) : Store() {

    private val currentKarutaQuizIdLiveData = MutableLiveData<KarutaQuizIdentifier?>()
    val currentKarutaQuizId: LiveData<KarutaQuizIdentifier?> = currentKarutaQuizIdLiveData

    private val resultLiveData = MutableLiveData<KarutaExam?>()
    val result: LiveData<KarutaExam?> = resultLiveData

    init {
        currentKarutaQuizIdLiveData.value = null
        resultLiveData.value = null

        register(dispatcher.on(StartExamAction::class.java).subscribe {
            currentKarutaQuizIdLiveData.value = it.karutaQuizId
        }, dispatcher.on(OpenNextQuizAction::class.java).subscribe {
            if (it.karutaQuizId != null) {
                currentKarutaQuizIdLiveData.value = it.karutaQuizId
            } else {
                // TODO: 見つからなかったらエラー
            }
        }, dispatcher.on(FinishExamAction::class.java).subscribe {
            currentKarutaQuizIdLiveData.value = null
            resultLiveData.value = it.karutaExam
        }, dispatcher.on(FetchExamAction::class.java).subscribe {
            if (it.karutaExam !== null) {
                resultLiveData.value = it.karutaExam
            } else {
                // TODO: 見つからなかったらエラー
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
