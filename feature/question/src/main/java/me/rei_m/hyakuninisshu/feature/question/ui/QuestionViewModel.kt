/*
 * Copyright (c) 2020. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package me.rei_m.hyakuninisshu.feature.question.ui

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.combineLatest
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.ui.AbstractViewModel
import me.rei_m.hyakuninisshu.feature.question.R
import me.rei_m.hyakuninisshu.state.core.Dispatcher
import me.rei_m.hyakuninisshu.state.question.action.QuestionActionCreator
import me.rei_m.hyakuninisshu.state.question.model.QuestionState
import me.rei_m.hyakuninisshu.state.question.store.QuestionStore
import me.rei_m.hyakuninisshu.state.training.model.DisplayStyleCondition
import java.util.*
import javax.inject.Inject

class QuestionViewModel(
    private val questionId: String,
    val kamiNoKuStyle: DisplayStyleCondition,
    val shimoNoKuStyle: DisplayStyleCondition,
    dispatcher: Dispatcher,
    private val actionCreator: QuestionActionCreator,
    private val store: QuestionStore,
    private val context: Context
) : AbstractViewModel(dispatcher) {

    val question = store.question

    val position = question.map { it.position }

    val yomiFudaWithState = question.map { it.yomiFuda }.combineLatest(store.state)

    val toriFudaList = question.map { it.toriFudaList }

    val state = store.state

    val isVisibleResult = state.map { it is QuestionState.Answered }

    val selectedTorifudaIndex = state.map {
        if (it is QuestionState.Answered) {
            return@map it.selectedToriFudaIndex
        } else {
            return@map null
        }
    }

    val isCorrectResId = state.map {
        val resId =
            if (it is QuestionState.Answered && it.isCorrect) R.drawable.check_correct else R.drawable.check_incorrect
        context.getDrawable(resId)
    }

    private var isSelected = false

    private val stateObserver = Observer<QuestionState> {
        when (it) {
            is QuestionState.Ready -> {
                dispatchAction {
                    actionCreator.startAnswer(questionId, Date())
                }
            }
            is QuestionState.InAnswer -> {
            }
            is QuestionState.Answered -> {
            }
        }
    }

    init {
        state.observeForever(stateObserver)

        dispatchAction {
            actionCreator.start(questionId, kamiNoKuStyle, shimoNoKuStyle)
        }
    }

    override fun onCleared() {
        state.removeObserver(stateObserver)
        store.dispose()
        super.onCleared()
    }

    fun onSelected(position: Int) {
        if (state.value !== QuestionState.InAnswer) {
            return
        }
        question.value?.let {
            if (isSelected) {
                return@let
            }
            isSelected = true
            dispatchAction {
                actionCreator.answer(questionId, it.toriFudaList[position], Date())
            }
        }
    }

    class Factory @Inject constructor(
        private val dispatcher: Dispatcher,
        private val actionCreator: QuestionActionCreator,
        private val store: QuestionStore,
        private val context: Context
    ) : ViewModelProvider.Factory {
        lateinit var questionId: String
        lateinit var kamiNoKuStyle: DisplayStyleCondition
        lateinit var shimoNoKuStyle: DisplayStyleCondition

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = QuestionViewModel(
            questionId,
            kamiNoKuStyle,
            shimoNoKuStyle,
            dispatcher,
            actionCreator,
            store,
            context
        ) as T
    }
}
