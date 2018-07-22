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

package me.rei_m.hyakuninisshu.presentation.core

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import me.rei_m.hyakuninisshu.action.quiz.QuizActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import me.rei_m.hyakuninisshu.ext.MutableLiveDataExt
import me.rei_m.hyakuninisshu.presentation.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.presentation.helper.Device
import me.rei_m.hyakuninisshu.presentation.helper.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class QuizViewModel(
        store: QuizStore,
        private val actionDispatcher: QuizActionDispatcher,
        device: Device,
        private val quizId: KarutaQuizIdentifier,
        kamiNoKuStyle: KarutaStyleFilter,
        shimoNoKuStyle: KarutaStyleFilter
) : LiveDataExt, MutableLiveDataExt {

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

    val openAnswerEvent: SingleLiveEvent<KarutaQuizIdentifier> = SingleLiveEvent()

    val quizTextSize: LiveData<Int>

    val choiceTextSize: LiveData<Int>

    val unhandledErrorEvent: LiveData<Void> = store.unhandledErrorEvent

    init {
        quizCount = content.map { it.currentPosition }

        val yomiFuda = content.map { it.yomiFuda(kamiNoKuStyle.value) }
        firstPhrase = yomiFuda.map { it.firstPhrase }
        secondPhrase = yomiFuda.map { it.secondPhrase }
        thirdPhrase = yomiFuda.map { it.thirdPhrase }

        val toriFudas = content.map { it.toriFudas(shimoNoKuStyle.value) }
        choiceFourthPhraseList = toriFudas.map { it.map { it.fourthPhrase } }
        choiceFifthPhraseList = toriFudas.map { it.map { it.fifthPhrase } }

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
        actionDispatcher.start(quizId, Date())
    }

    fun onClickChoice(choiceNoValue: Int) {
        actionDispatcher.answer(quizId, ChoiceNo.forValue(choiceNoValue), Date())
    }

    fun onClickResult() {
        openAnswerEvent.value = quizId
    }

    class Factory @Inject constructor(private val actionDispatcher: QuizActionDispatcher,
                                      private val device: Device) {
        fun create(store: QuizStore,
                   quizId: KarutaQuizIdentifier,
                   kamiNoKuStyle: KarutaStyleFilter,
                   shimoNoKuStyle: KarutaStyleFilter): QuizViewModel = QuizViewModel(
                store,
                actionDispatcher,
                device,
                quizId,
                kamiNoKuStyle,
                shimoNoKuStyle
        )
    }
}
