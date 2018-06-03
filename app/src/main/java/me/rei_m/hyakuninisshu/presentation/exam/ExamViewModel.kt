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

package me.rei_m.hyakuninisshu.presentation.exam

import android.arch.lifecycle.LiveData
import me.rei_m.hyakuninisshu.action.exam.ExamActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import javax.inject.Inject

class ExamViewModel(
        store: ExamStore,
        private val actionDispatcher: ExamActionDispatcher
) : LiveDataExt {

    val currentKarutaQuizId: LiveData<KarutaQuizIdentifier?> = store.currentKarutaQuizId

    val isVisibleAd: LiveData<Boolean> = currentKarutaQuizId.map { it == null }

    fun startExam() {
        actionDispatcher.start()
    }

    fun fetchNext() {
        actionDispatcher.fetchNext()
    }

    class Factory @Inject constructor(private val actionDispatcher: ExamActionDispatcher) {
        fun create(store: ExamStore): ExamViewModel = ExamViewModel(store, actionDispatcher)
    }
}
