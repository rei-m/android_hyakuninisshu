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
import me.rei_m.hyakuninisshu.state.exam.action.FetchExamResultAction
import me.rei_m.hyakuninisshu.state.exam.model.ExamResult
import me.rei_m.hyakuninisshu.state.material.model.Material
import javax.inject.Inject

/**
 * 力試しの結果の状態を管理する.
 */
class ExamResultStore
    @Inject
    constructor(
        dispatcher: Dispatcher,
    ) : Store() {
        /**
         * 力試しの結果.
         */
        private val _result = MutableLiveData<ExamResult?>()
        val result: LiveData<ExamResult?> = _result

        /**
         * 全百人一首の資料の情報.
         */
        private val _materialList = MutableLiveData<List<Material>?>()
        val materialList: LiveData<List<Material>?> = _materialList

        private val _isFailure = MutableLiveData(false)
        val isFailure: LiveData<Boolean> = _isFailure

        init {
            register(
                dispatcher.on(FetchExamResultAction::class.java).subscribe {
                    when (it) {
                        is FetchExamResultAction.Success -> {
                            _result.value = it.examResult
                            _materialList.value = it.materialList
                            _isFailure.value = false
                        }
                        is FetchExamResultAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
            )
        }
    }
