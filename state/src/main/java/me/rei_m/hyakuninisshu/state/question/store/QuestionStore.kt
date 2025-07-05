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

package me.rei_m.hyakuninisshu.state.question.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.core.Store
import me.rei_m.hyakuninisshu.state.question.action.AnswerQuestionAction
import me.rei_m.hyakuninisshu.state.question.action.StartAnswerQuestionAction
import me.rei_m.hyakuninisshu.state.question.action.StartQuestionAction
import me.rei_m.hyakuninisshu.state.question.model.Question
import me.rei_m.hyakuninisshu.state.question.model.QuestionState
import javax.inject.Inject

/**
 * 問題の状態を管理する.
 */
class QuestionStore
    @Inject
    constructor(
        dispatcher: Dispatcher,
    ) : Store() {
        private val _question = MutableLiveData<Question>()
        val question: LiveData<Question> = _question

        private val _state = MutableLiveData<QuestionState>()
        val state: LiveData<QuestionState> = _state

        private val _isFailure = MutableLiveData(false)
        val isFailure: LiveData<Boolean> = _isFailure

        init {
            register(
                dispatcher.on(StartQuestionAction::class.java).subscribe {
                    when (it) {
                        is StartQuestionAction.Success -> {
                            _question.value = it.question
                            _state.value = it.state
                            _isFailure.value = false
                        }
                        is StartQuestionAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
                dispatcher.on(StartAnswerQuestionAction::class.java).subscribe {
                    when (it) {
                        is StartAnswerQuestionAction.Success -> {
                            _state.value = it.state
                            _isFailure.value = false
                        }
                        is StartAnswerQuestionAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
                dispatcher.on(AnswerQuestionAction::class.java).subscribe {
                    when (it) {
                        is AnswerQuestionAction.Success -> {
                            _state.value = it.state
                            _isFailure.value = false
                        }
                        is AnswerQuestionAction.Failure -> {
                            _isFailure.value = true
                        }
                    }
                },
            )
        }
    }
