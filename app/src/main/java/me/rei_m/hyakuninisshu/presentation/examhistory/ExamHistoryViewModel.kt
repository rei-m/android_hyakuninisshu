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
package me.rei_m.hyakuninisshu.presentation.examhistory

import android.arch.lifecycle.LiveData
import android.support.v4.app.FragmentActivity
import me.rei_m.hyakuninisshu.action.exam.ExamActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.ext.map
import me.rei_m.hyakuninisshu.presentation.ViewModelFactory
import javax.inject.Inject

class ExamHistoryViewModel(store: ExamHistoryStore, actionDispatcher: ExamActionDispatcher) {

    val isLoading: LiveData<Boolean> = store.karutaExamList.map { it == null }

    val karutaExamList: LiveData<List<KarutaExam>?> = store.karutaExamList

    val unhandledErrorEvent = store.unhandledErrorEvent

    init {
        actionDispatcher.fetchAll()
    }

    class Factory @Inject constructor(
        private val actionDispatcher: ExamActionDispatcher,
        private val storeFactory: ExamHistoryStore.Factory
    ) : ViewModelFactory {
        fun create(activity: FragmentActivity) = ExamHistoryViewModel(
            obtainActivityStore(activity, ExamHistoryStore::class.java, storeFactory),
            actionDispatcher
        )
    }
}
