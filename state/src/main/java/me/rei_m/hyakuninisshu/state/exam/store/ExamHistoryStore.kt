/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.state.exam.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.core.Store
import me.rei_m.hyakuninisshu.state.exam.action.FetchAllExamResultAction
import me.rei_m.hyakuninisshu.state.exam.model.ExamResult
import javax.inject.Inject

/**
 * 力試しの履歴を管理する.
 */
class ExamHistoryStore
    @Inject
    constructor(
        dispatcher: Dispatcher,
    ) : Store() {
        private val _resultList = MutableLiveData<List<ExamResult>?>(null)
        val resultList: LiveData<List<ExamResult>?> = _resultList

        private val _isFailure = MutableLiveData(false)
        val isFailure: LiveData<Boolean> = _isFailure

        init {
            register(
                dispatcher.on(FetchAllExamResultAction::class.java).subscribe {
                    when (it) {
                        is FetchAllExamResultAction.Success -> {
                            _resultList.value = it.examResultList
                        }
                        is FetchAllExamResultAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
            )
        }
    }
