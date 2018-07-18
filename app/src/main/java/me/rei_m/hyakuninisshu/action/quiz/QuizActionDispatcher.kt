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

package me.rei_m.hyakuninisshu.action.quiz

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function3
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import me.rei_m.hyakuninisshu.ext.SingleExt
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizActionDispatcher @Inject constructor(
        private val dispatcher: Dispatcher,
        private val karutaRepository: KarutaRepository,
        private val karutaQuizRepository: KarutaQuizRepository
) : SingleExt {

    /**
     * 指定の問題を取り出す.
     *
     * @param karutaQuizId 問題ID.
     */
    fun fetch(karutaQuizId: KarutaQuizIdentifier) {
        karutaQuizRepository.findBy(karutaQuizId).flatMap { it.content() }.subscribeNew({
            dispatcher.dispatch(FetchQuizAction(it))
        }, {
            dispatcher.dispatch(FetchQuizAction(null))
        })
    }

    /**
     * 指定の問題の回答を開始する.
     *
     * @param karutaQuizId 問題ID.
     * @param startTime 開始時間.
     */
    fun start(karutaQuizId: KarutaQuizIdentifier, startTime: Date) {
        karutaQuizRepository.findBy(karutaQuizId).flatMap {
            if (it.result == null) {
                return@flatMap karutaQuizRepository.store(it.start(startTime)).toSingleDefault(it)
            }
            return@flatMap Single.just(it)
        }.flatMap {
            it.content()
        }.subscribeNew({
            dispatcher.dispatch(StartQuizAction(it))
        }, {
            dispatcher.dispatch(StartQuizAction(null))
        })
    }

    /**
     * 指定の問題の回答をする.
     *
     * @param karutaQuizId 問題ID.
     * @param choiceNo 選択した回答の番号.
     * @param answerTime 回答時間.
     */
    fun answer(karutaQuizId: KarutaQuizIdentifier, choiceNo: ChoiceNo, answerTime: Date) {
        karutaQuizRepository.findBy(karutaQuizId).flatMap {
            karutaQuizRepository.store(it.verify(choiceNo, answerTime)).toSingleDefault(it)
        }.flatMap {
            it.content()
        }.subscribeNew({
            dispatcher.dispatch(AnswerQuizAction(it))
        }, { throwable ->
            dispatcher.dispatch(AnswerQuizAction(null))
        })
    }

    private fun KarutaQuiz.content(): Single<KarutaQuizContent> {
        val choiceSingle: Single<List<Karuta>> = Observable.fromIterable<KarutaIdentifier>(choiceList)
                .flatMapSingle<Karuta> { karutaRepository.findBy(it) }
                .toList()

        val countSingle: Single<KarutaQuizCounter> = karutaQuizRepository.countQuizByAnswered()

        val existNextQuizSingle: Single<Boolean> = karutaQuizRepository.existNextQuiz()

        return Single.zip(choiceSingle, countSingle, existNextQuizSingle, Function3 { choice, count, existNext ->
            val correct = choice[choiceList.indexOf(correctId)]
            KarutaQuizContent(this, correct, choice, count, existNext)
        })
    }
}
