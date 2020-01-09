/*
 * Copyright (c) 2019. Rei Matsushita
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

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizActionCreator @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val karutaQuizRepository: KarutaQuizRepository
) {
    /**
     * 指定の問題を取り出す.
     *
     * @param karutaQuizId 問題ID.
     * @return FetchQuizAction
     * @throws NoSuchElementException 歌が見つからなかった場合
     */
    fun fetch(karutaQuizId: KarutaQuizIdentifier) = try {
        val karutaQuiz = karutaQuizRepository.findBy(karutaQuizId)
            ?: throw NoSuchElementException(karutaQuizId.toString())
        FetchQuizAction.createSuccess(karutaQuiz.content())
    } catch (e: Exception) {
        FetchQuizAction.createError(e)
    }

    /**
     * 指定の問題の回答を開始する.
     *
     * @param karutaQuizId 問題ID.
     * @param startTime 開始時間.
     * @return StartQuizAction
     * @throws NoSuchElementException 問題が見つからなかった場合
     */
    fun start(karutaQuizId: KarutaQuizIdentifier, startTime: Date) = try {
        val karutaQuiz = karutaQuizRepository.findBy(karutaQuizId)
            ?: throw NoSuchElementException(karutaQuizId.toString())
        if (karutaQuiz.state != KarutaQuiz.State.ANSWERED) {
            karutaQuizRepository.store(karutaQuiz.start(startTime))
        }
        StartQuizAction.createSuccess(karutaQuiz.content())
    } catch (e: Exception) {
        StartQuizAction.createError(e)
    }

    /**
     * 指定の問題の回答をする.
     *
     * @param karutaQuizId 問題ID.
     * @param choiceNo 選択した回答の番号.
     * @param answerTime 回答時間.
     * @return AnswerQuizAction
     * @throws NoSuchElementException 問題が見つからなかった場合
     */
    fun answer(karutaQuizId: KarutaQuizIdentifier, choiceNo: ChoiceNo, answerTime: Date) = try {
        val karutaQuiz = karutaQuizRepository.findBy(karutaQuizId)
            ?: throw NoSuchElementException(karutaQuizId.toString())
        karutaQuizRepository.store(karutaQuiz.verify(choiceNo, answerTime))
        AnswerQuizAction.createSuccess(karutaQuiz.content())
    } catch (e: Exception) {
        AnswerQuizAction.createError(e)
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
