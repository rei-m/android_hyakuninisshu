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
import me.rei_m.hyakuninisshu.action.quiz.QuizActionDispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.LiveDataExt
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import me.rei_m.hyakuninisshu.presentation.util.SingleLiveEvent
import javax.inject.Inject

class QuizAnswerViewModel(
        store: QuizStore,
        private val actionDispatcher: QuizActionDispatcher,
        private val quizId: KarutaQuizIdentifier,
        private val navigator: Navigator
) : LiveDataExt {

    val content: LiveData<KarutaQuizContent> = store.karutaQuizContent

    val existNextQuiz: LiveData<Boolean> = content.map { it.existNext }

    val karuta: LiveData<Karuta> = content.map { it.correct }

    val karutaNo: LiveData<Int> = karuta.map { it.identifier().value }

    val kimariji: LiveData<Int> = karuta.map { it.kimariji.value }

    val creator: LiveData<String> = karuta.map { it.creator }

    val firstPhrase: LiveData<String> = karuta.map { it.kamiNoKu.first.kanji.padEnd(5, ' ') }

    val secondPhrase: LiveData<String> = karuta.map { it.kamiNoKu.second.kanji }

    val thirdPhrase: LiveData<String> = karuta.map { it.kamiNoKu.third.kanji }

    val fourthPhrase: LiveData<String> = karuta.map { it.shimoNoKu.fourth.kanji.padEnd(7, ' ') }

    val fifthPhrase: LiveData<String> = karuta.map { it.shimoNoKu.fifth.kanji }

    val openNextQuizEvent: SingleLiveEvent<Void> = SingleLiveEvent()

    val openResultEvent: SingleLiveEvent<Void> = SingleLiveEvent()

    fun start() {
        actionDispatcher.fetch(quizId)
    }

    fun onClickAnswer() {
        karuta.value?.let {
            navigator.navigateToKaruta(it.identifier())
        }
    }

    fun onClickNextQuiz() {
        openNextQuizEvent.call()
    }

    fun onClickConfirmResult() {
        openResultEvent.call()
    }

    class Factory @Inject constructor(private val actionDispatcher: QuizActionDispatcher,
                                      private val navigator: Navigator) {
        fun create(store: QuizStore,
                   quizId: KarutaQuizIdentifier): QuizAnswerViewModel = QuizAnswerViewModel(
                store,
                actionDispatcher,
                quizId,
                navigator
        )
    }
}
