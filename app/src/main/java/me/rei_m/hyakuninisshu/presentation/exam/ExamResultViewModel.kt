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
package me.rei_m.hyakuninisshu.presentation.exam

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import me.rei_m.hyakuninisshu.action.exam.ExamActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizJudgement
import me.rei_m.hyakuninisshu.ext.map
import me.rei_m.hyakuninisshu.presentation.ViewModelFactory
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.util.AnalyticsHelper
import me.rei_m.hyakuninisshu.util.Event
import javax.inject.Inject

class ExamResultViewModel(
    store: ExamStore,
    actionDispatcher: ExamActionDispatcher,
    initialKarutaExamId: KarutaExamIdentifier?,
    private val navigator: Navigator,
    private val analyticsHelper: AnalyticsHelper
) {

    val karutaExamId: LiveData<KarutaExamIdentifier?> = store.result.map { it?.identifier }

    val score: LiveData<String?> = store.result.map { it?.result?.score }

    val averageAnswerSec: LiveData<Float?> = store.result.map { it?.result?.averageAnswerSec }

    val karutaQuizJudgements: LiveData<List<KarutaQuizJudgement>?> = store.result.map { it?.result?.judgements }

    val notFoundExamEvent: LiveData<Event<Unit>> = store.notFoundExamEvent

    init {
        if (initialKarutaExamId == null) {
            actionDispatcher.finish()
        } else {
            actionDispatcher.fetch(initialKarutaExamId)
        }
    }

    fun openKaruta(karutaId: KarutaIdentifier) {
        navigator.navigateToKaruta(karutaId)
    }

    fun finish() {
        analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.FINISH_EXAM)
        navigator.back()
    }

    class Factory @Inject constructor(
        private val actionDispatcher: ExamActionDispatcher,
        private val storeFactory: ExamStore.Factory,
        private val navigator: Navigator,
        private val analyticsHelper: AnalyticsHelper
    ) : ViewModelFactory {

        var initialKarutaExamId: KarutaExamIdentifier? = null

        fun create(activity: FragmentActivity) = ExamResultViewModel(
            obtainActivityStore(activity, ExamStore::class.java, storeFactory),
            actionDispatcher,
            initialKarutaExamId,
            navigator,
            analyticsHelper
        )
    }
}
