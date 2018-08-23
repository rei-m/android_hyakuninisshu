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
package me.rei_m.hyakuninisshu.action.quiz

import kotlinx.coroutines.experimental.launch
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import java.util.Date

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

@Singleton
class QuizActionDispatcher @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val karutaQuizRepository: KarutaQuizRepository,
    private val dispatcher: Dispatcher,
    private val coroutineContext: CoroutineContext
) {

    /**
     * 指定の問題を取り出す.
     *
     * @param karutaQuizId 問題ID.
     */
    fun fetch(karutaQuizId: KarutaQuizIdentifier) {
        launch(coroutineContext) {
            try {
                val karutaQuiz = karutaQuizRepository.findBy(karutaQuizId)
                    ?: throw NoSuchElementException(karutaQuizId.toString())
                dispatcher.dispatch(FetchQuizAction.createSuccess(karutaQuiz.content()))
            } catch (e: Exception) {
                dispatcher.dispatch(FetchQuizAction.createError(e))
            }
        }
    }

    /**
     * 指定の問題の回答を開始する.
     *
     * @param karutaQuizId 問題ID.
     * @param startTime 開始時間.
     */
    fun start(karutaQuizId: KarutaQuizIdentifier, startTime: Date) {
        launch(coroutineContext) {
            try {
                val karutaQuiz = karutaQuizRepository.findBy(karutaQuizId)
                    ?: throw NoSuchElementException(karutaQuizId.toString())
                if (karutaQuiz.state != KarutaQuiz.State.ANSWERED) {
                    karutaQuizRepository.store(karutaQuiz.start(startTime))
                }
                dispatcher.dispatch(StartQuizAction.createSuccess(karutaQuiz.content()))
            } catch (e: Exception) {
                dispatcher.dispatch(StartQuizAction.createError(e))
            }
        }
    }

    /**
     * 指定の問題の回答をする.
     *
     * @param karutaQuizId 問題ID.
     * @param choiceNo 選択した回答の番号.
     * @param answerTime 回答時間.
     */
    fun answer(karutaQuizId: KarutaQuizIdentifier, choiceNo: ChoiceNo, answerTime: Date) {
        launch(coroutineContext) {
            try {
                val karutaQuiz = karutaQuizRepository.findBy(karutaQuizId)
                    ?: throw NoSuchElementException(karutaQuizId.toString())
                karutaQuizRepository.store(karutaQuiz.verify(choiceNo, answerTime))
                dispatcher.dispatch(AnswerQuizAction.createSuccess(karutaQuiz.content()))
            } catch (e: Exception) {
                dispatcher.dispatch(AnswerQuizAction.createError(e))
            }
        }
    }

    private fun KarutaQuiz.content(): KarutaQuizContent {
        val choiceKarutaList = choiceList.mapNotNull { karutaRepository.findBy(it) }
        val counter = karutaQuizRepository.countQuizByAnswered()
        val correctKaruta = choiceKarutaList[choiceList.indexOf(correctId)]
        return KarutaQuizContent(
            this,
            correctKaruta,
            choiceKarutaList,
            counter,
            counter.existNext
        )
    }
}
