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
package me.rei_m.hyakuninisshu.presentation.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.training.AggregateResultsAction
import me.rei_m.hyakuninisshu.action.training.OpenNextQuizAction
import me.rei_m.hyakuninisshu.action.training.StartTrainingAction
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Store
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event
import javax.inject.Inject

@ActivityScope
class TrainingStore @Inject constructor(dispatcher: Dispatcher) : Store() {

    private val _currentKarutaQuizId = MutableLiveData<KarutaQuizIdentifier?>()
    val currentKarutaQuizId: LiveData<KarutaQuizIdentifier?> = _currentKarutaQuizId

    private val _result = MutableLiveData<TrainingResult>()
    val result: LiveData<TrainingResult> = _result

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = _empty

    private val _unhandledErrorEvent = MutableLiveData<Event<Unit>>()
    val unhandledErrorEvent: LiveData<Event<Unit>> = _unhandledErrorEvent

    init {
        _empty.value = false
        register(dispatcher.on(StartTrainingAction::class.java).subscribe {
            if (!it.isSucceeded) {
                _unhandledErrorEvent.value = Event(Unit)
                return@subscribe
            }
            if (it.karutaQuizId == null) {
                _empty.value = true
            } else {
                _currentKarutaQuizId.value = it.karutaQuizId
            }
        }, dispatcher.on(OpenNextQuizAction::class.java).subscribe {
            if (it.isSucceeded) {
                _currentKarutaQuizId.value = it.karutaQuizId
            } else {
                _unhandledErrorEvent.value = Event(Unit)
            }
        }, dispatcher.on(AggregateResultsAction::class.java).subscribe {
            if (!it.isSucceeded) {
                _unhandledErrorEvent.value = Event(Unit)
                return@subscribe
            }
            _currentKarutaQuizId.value = null
            if (it.trainingResult == null) {
                _empty.value = true
            } else {
                _result.value = it.trainingResult
            }
        })
    }
}
