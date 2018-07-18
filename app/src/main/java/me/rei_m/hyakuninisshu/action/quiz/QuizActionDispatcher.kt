/*
 * Copyright (c) 2017. Rei Matsushita
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


class QuizActionDispatcher @Inject constructor(
        private val dispatcher: Dispatcher,
        private val karutaRepository: KarutaRepository,
        private val karutaQuizRepository: KarutaQuizRepository
) : SingleExt {

    fun fetch(quizId: KarutaQuizIdentifier) {
        karutaQuizRepository.findBy(quizId)
                .flatMap { this.createContent(it) }
                .subscribeNew { dispatcher.dispatch(FetchQuizAction(it)) }
    }

    fun start(karutaQuizId: KarutaQuizIdentifier, startTime: Date) {
        karutaQuizRepository.findBy(karutaQuizId).flatMap {
            if (it.result == null) {
                return@flatMap karutaQuizRepository.store(it.start(startTime)).toSingleDefault(it)
            }
            return@flatMap Single.just(it)
        }.flatMap {
            this.createContent(it)
        }.subscribeNew({
            dispatcher.dispatch(StartQuizAction(it))
        }, { println(it) })
    }

    fun answer(quizId: KarutaQuizIdentifier, choiceNo: ChoiceNo, answerTime: Date) {
        karutaQuizRepository.findBy(quizId)
                .flatMap { karutaQuizRepository.store(it.verify(choiceNo, answerTime)).toSingleDefault(it) }
                .flatMap { this.createContent(it) }
                .subscribeNew({
                    dispatcher.dispatch(AnswerQuizAction(it))
                }, { throwable ->
                    dispatcher.dispatch(AnswerQuizAction())
                })
    }

    fun finish() {
        dispatcher.dispatch(FinishQuizAction())
    }

    private fun createContent(karutaQuiz: KarutaQuiz): Single<KarutaQuizContent> {
        val choiceSingle: Single<List<Karuta>> = Observable.fromIterable<KarutaIdentifier>(karutaQuiz.choiceList)
                .flatMapSingle<Karuta> { karutaRepository.findBy(it) }
                .toList()

        val countSingle: Single<KarutaQuizCounter> = karutaQuizRepository.countQuizByAnswered()

        val existNextQuizSingle: Single<Boolean> = karutaQuizRepository.existNextQuiz()

        return Single.zip(choiceSingle, countSingle, existNextQuizSingle, Function3<List<Karuta>, KarutaQuizCounter, Boolean, KarutaQuizContent> { t1, t2, t3 ->
            val correct = t1[karutaQuiz.choiceList.indexOf(karutaQuiz.correctId)]
            KarutaQuizContent(karutaQuiz, correct, t1, t2, t3)
        })
    }
}
