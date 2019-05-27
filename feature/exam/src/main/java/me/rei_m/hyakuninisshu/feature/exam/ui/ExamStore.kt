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
package me.rei_m.hyakuninisshu.feature.exam.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.exam.FetchExamAction
import me.rei_m.hyakuninisshu.action.exam.FinishExamAction
import me.rei_m.hyakuninisshu.action.exam.OpenNextQuizAction
import me.rei_m.hyakuninisshu.action.exam.StartExamAction
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Store
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event
import javax.inject.Inject

@ActivityScope
class ExamStore @Inject constructor(dispatcher: Dispatcher) : Store() {

    private val _currentKarutaQuizId = MutableLiveData<KarutaQuizIdentifier?>()
    val currentKarutaQuizId: LiveData<KarutaQuizIdentifier?> = _currentKarutaQuizId

    private val _result = MutableLiveData<KarutaExam?>()
    val result: LiveData<KarutaExam?> = _result

    private val _notFoundQuizEvent = MutableLiveData<Event<Unit>>()
    val notFoundQuizEvent = _notFoundQuizEvent

    private val _notFoundExamEvent = MutableLiveData<Event<Unit>>()
    val notFoundExamEvent = _notFoundExamEvent

    init {
        _currentKarutaQuizId.value = null
        _result.value = null

        register(dispatcher.on(StartExamAction::class.java).subscribe {
            if (it.isSucceeded) {
                _currentKarutaQuizId.value = it.karutaQuizId
            } else {
                _notFoundQuizEvent.value = Event(Unit)
            }
        }, dispatcher.on(OpenNextQuizAction::class.java).subscribe {
            if (it.isSucceeded) {
                _currentKarutaQuizId.value = it.karutaQuizId
            } else {
                _notFoundQuizEvent.value = Event(Unit)
            }
        }, dispatcher.on(FinishExamAction::class.java).subscribe {
            _currentKarutaQuizId.value = null
            if (it.isSucceeded) {
                _result.value = it.karutaExam
            } else {
                _notFoundExamEvent.value = Event(Unit)
            }
        }, dispatcher.on(FetchExamAction::class.java).subscribe {
            if (it.isSucceeded) {
                _result.value = it.karutaExam
            } else {
                _notFoundExamEvent.value = Event(Unit)
            }
        })
    }
}