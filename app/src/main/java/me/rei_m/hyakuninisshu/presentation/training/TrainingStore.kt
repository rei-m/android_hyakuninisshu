/*
 * Copyright (c) 2017. Rei Matsushita
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

package me.rei_m.hyakuninisshu.presentation.training

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.training.AggregateResultsAction
import me.rei_m.hyakuninisshu.action.training.OpenNextQuizAction
import me.rei_m.hyakuninisshu.action.training.StartTrainingAction
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult
import me.rei_m.hyakuninisshu.presentation.helper.SingleLiveEvent
import javax.inject.Inject

class TrainingStore(dispatcher: Dispatcher) : ViewModel() {

    private val currentKarutaQuizIdLiveData = MutableLiveData<KarutaQuizIdentifier?>()
    val currentKarutaQuizId: LiveData<KarutaQuizIdentifier?> = currentKarutaQuizIdLiveData

    private val resultLiveData = MutableLiveData<TrainingResult>()
    val result: LiveData<TrainingResult> = resultLiveData

    private val notFoundErrorEventLiveData = SingleLiveEvent<Boolean>()
    val notFoundErrorEvent: LiveData<Boolean> = notFoundErrorEventLiveData

    private val disposable = CompositeDisposable()

    init {
        disposable.addAll(dispatcher.on(StartTrainingAction::class.java).subscribe {
            if (it.karutaQuizId == null) {
                notFoundErrorEventLiveData.value = true
            } else {
                currentKarutaQuizIdLiveData.value = it.karutaQuizId
            }
        }, dispatcher.on(OpenNextQuizAction::class.java).subscribe {
            currentKarutaQuizIdLiveData.value = it.karutaQuizId
        }, dispatcher.on(AggregateResultsAction::class.java).subscribe {
            currentKarutaQuizIdLiveData.value = null
            if (it.trainingResult == null) {
                notFoundErrorEventLiveData.value = true
            } else {
                resultLiveData.value = it.trainingResult
            }
        })
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(private val dispatcher: Dispatcher) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrainingStore(dispatcher) as T
        }
    }
}
