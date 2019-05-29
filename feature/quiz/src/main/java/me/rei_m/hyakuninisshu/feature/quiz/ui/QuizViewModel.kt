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
package me.rei_m.hyakuninisshu.feature.quiz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.rei_m.hyakuninisshu.action.quiz.QuizActionCreator
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.ToriFuda
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.map
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.withValue
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.Device
import me.rei_m.hyakuninisshu.feature.corecomponent.lifecycle.AbstractViewModel
import java.util.*
import kotlin.coroutines.CoroutineContext

class QuizViewModel(
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val store: QuizStore,
    private val actionCreator: QuizActionCreator,
    private val quizId: KarutaQuizIdentifier,
    kamiNoKuStyle: KarutaStyleFilter,
    shimoNoKuStyle: KarutaStyleFilter,
    device: Device
) : AbstractViewModel(mainContext) {

    val content: LiveData<KarutaQuizContent> = store.karutaQuizContent

    val isVisibleContent: LiveData<Boolean> = store.karutaQuizContent.map {
        true
    }

    val quizCount: LiveData<String>

    val firstPhrase: LiveData<String>

    val secondPhrase: LiveData<String>

    val thirdPhrase: LiveData<String>

    val choiceFourthPhraseList: LiveData<List<String>>

    val choiceFifthPhraseList: LiveData<List<String>>

    val isVisibleChoiceList: LiveData<List<Boolean>>

    val isVisibleResult: LiveData<Boolean>

    val isCorrect: LiveData<Boolean?>

    private val _openAnswerEvent = MutableLiveData<Event<KarutaQuizIdentifier>>()
    val openAnswerEvent: LiveData<Event<KarutaQuizIdentifier>> = _openAnswerEvent

    val quizTextSize: LiveData<Int>

    val choiceTextSize: LiveData<Int>

    val unhandledErrorEvent: LiveData<Event<Unit>> = store.unhandledErrorEvent

    init {
        quizCount = content.map { it.currentPosition }

        val yomiFuda = content.map { it.yomiFuda(kamiNoKuStyle.value) }
        firstPhrase = yomiFuda.map { it.firstPhrase }
        secondPhrase = yomiFuda.map { it.secondPhrase }
        thirdPhrase = yomiFuda.map { it.thirdPhrase }

        val toriFudas = content.map { it.toriFudas(shimoNoKuStyle.value) }
        choiceFourthPhraseList = toriFudas.map { it.map(ToriFuda::fourthPhrase) }
        choiceFifthPhraseList = toriFudas.map { it.map(ToriFuda::fifthPhrase) }

        val result = content.map { it.quiz.result }
        isVisibleChoiceList = result.map {
            if (it == null) {
                Arrays.asList(true, true, true, true)
            } else {
                Arrays.asList(false, false, false, false).apply {
                    this[it.choiceNo.asIndex] = true
                }
            }
        }
        isVisibleResult = result.map { it != null }
        isCorrect = result.map { it?.judgement?.isCorrect }

        // 問題を縦表示スクロールなしで収めるための微妙な対応
        val windowWidth = device.windowSize.x
        val windowHeight = device.windowSize.y

        val phraseHeight = windowHeight / 2
        quizTextSize = MutableLiveData<Int>().withValue(phraseHeight / 13)

        val choiceWidth = windowWidth / 4
        choiceTextSize = MutableLiveData<Int>().withValue(choiceWidth / 5)
    }

    fun start() {
        launch {
            withContext(ioContext) { actionCreator.start(quizId, Date()) }
        }
    }

    fun onClickChoice(choiceNoValue: Int) {
        launch {
            withContext(ioContext) { actionCreator.answer(quizId, ChoiceNo.forValue(choiceNoValue), Date()) }
        }
    }

    fun onClickResult() {
        _openAnswerEvent.value = Event(quizId)
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory(
        private val mainContext: CoroutineContext,
        private val ioContext: CoroutineContext,
        private val store: QuizStore,
        private val actionCreator: QuizActionCreator,
        private val quizId: KarutaQuizIdentifier,
        private val kamiNoKuStyle: KarutaStyleFilter,
        private val shimoNoKuStyle: KarutaStyleFilter,
        private val device: Device
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuizViewModel(
                mainContext,
                ioContext,
                store,
                actionCreator,
                quizId,
                kamiNoKuStyle,
                shimoNoKuStyle,
                device
            ) as T
        }
    }
}
