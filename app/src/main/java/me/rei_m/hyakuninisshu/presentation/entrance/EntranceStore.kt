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
package me.rei_m.hyakuninisshu.presentation.entrance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.action.exam.FetchRecentExamAction
import me.rei_m.hyakuninisshu.action.exam.FinishExamAction
import me.rei_m.hyakuninisshu.action.material.EditMaterialAction
import me.rei_m.hyakuninisshu.action.material.FetchMaterialAction
import me.rei_m.hyakuninisshu.di.ForActivity
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.presentation.Store
import javax.inject.Inject

@ForActivity
class EntranceStore @Inject constructor(dispatcher: Dispatcher) : Store() {

    private val _recentExam = MutableLiveData<KarutaExam?>()
    val recentExam: LiveData<KarutaExam?> = _recentExam

    private val _karutaList = MutableLiveData<List<Karuta>>()
    val karutaList: LiveData<List<Karuta>> = _karutaList

    init {
        register(dispatcher.on(FetchMaterialAction::class.java).subscribe {
            if (it.isSucceeded) {
                _karutaList.value = it.karutas?.asList()
            }
        }, dispatcher.on(EditMaterialAction::class.java).subscribe { action ->
            _karutaList.value?.let {
                val karutaList = ArrayList(it)
                karutaList[karutaList.indexOf(action.karuta)] = action.karuta
                _karutaList.value = karutaList
            }
        }, dispatcher.on(FetchRecentExamAction::class.java).subscribe {
            if (it.isSucceeded) {
                _recentExam.value = it.karutaExam
            }
        }, dispatcher.on(FinishExamAction::class.java).subscribe {
            if (it.isSucceeded) {
                _recentExam.value = it.karutaExam
            }
        })
    }
}
