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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import me.rei_m.hyakuninisshu.action.exam.ExamActionCreator
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizJudgement
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class ExamResultViewModel(
    coroutineContext: CoroutineContext,
    private val store: ExamStore,
    actionCreator: ExamActionCreator,
    initialKarutaExamId: KarutaExamIdentifier?,
    private val analyticsHelper: AnalyticsHelper
) : AbstractViewModel(coroutineContext) {

    val karutaExamId: LiveData<KarutaExamIdentifier?> = store.result.map { it?.identifier }

    val score: LiveData<String?> = store.result.map { it?.result?.score }

    val averageAnswerSec: LiveData<Float?> = store.result.map { it?.result?.averageAnswerSec }

    val karutaQuizJudgements: LiveData<List<KarutaQuizJudgement>?> = store.result.map { it?.result?.judgements }

    val notFoundExamEvent: LiveData<Event<Unit>> = store.notFoundExamEvent

    init {
        if (initialKarutaExamId == null) {
            launch {
                actionCreator.finish()
            }
        } else {
            launch {
                actionCreator.fetch(initialKarutaExamId)
            }
        }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        @Named("vmCoroutineContext") private val coroutineContext: CoroutineContext,
        private val store: ExamStore,
        private val actionCreator: ExamActionCreator,
        private val analyticsHelper: AnalyticsHelper
    ) : ViewModelProvider.Factory {
        var initialKarutaExamId: KarutaExamIdentifier? = null

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamResultViewModel(
                coroutineContext,
                store,
                actionCreator,
                initialKarutaExamId,
                analyticsHelper
            ) as T
        }
    }
}
