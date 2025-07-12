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
import me.rei_m.hyakuninisshu.state.exam.action.FetchRecentExamResultAction
import me.rei_m.hyakuninisshu.state.exam.action.FinishExamAction
import me.rei_m.hyakuninisshu.state.exam.model.ExamResult
import javax.inject.Inject

/**
 * 力試しメニューの状態を管理する.
 */
class ExamMenuStore
    @Inject
    constructor(
        dispatcher: Dispatcher,
    ) : Store() {
        /**
         * 最新の力試しの結果.
         */
        private val _recentResult = MutableLiveData<ExamResult?>()
        val recentResult: LiveData<ExamResult?> = _recentResult

        private val _isFailure = MutableLiveData(false)
        val isFailure: LiveData<Boolean> = _isFailure

        init {
            register(
                dispatcher.on(FetchRecentExamResultAction::class.java).subscribe {
                    when (it) {
                        is FetchRecentExamResultAction.Success -> {
                            _recentResult.value = it.examResult
                            _isFailure.value = false
                        }
                        is FetchRecentExamResultAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
                dispatcher.on(FinishExamAction::class.java).subscribe {
                    when (it) {
                        is FinishExamAction.Success -> {
                            _recentResult.value = it.examResult
                            _isFailure.value = false
                        }
                        is FinishExamAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
            )
        }
    }
