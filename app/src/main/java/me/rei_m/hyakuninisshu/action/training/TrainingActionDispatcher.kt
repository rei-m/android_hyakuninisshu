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

package me.rei_m.hyakuninisshu.action.training

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.*
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import me.rei_m.hyakuninisshu.ext.SingleExt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingActionDispatcher @Inject constructor(
        private val dispatcher: Dispatcher,
        private val karutaRepository: KarutaRepository,
        private val karutaQuizRepository: KarutaQuizRepository,
        private val karutaExamRepository: KarutaExamRepository
) : SingleExt {
    /**
     * 練習を開始する.
     *
     * @param fromKarutaId 練習範囲の開始歌ID
     * @param toKarutaId   練習範囲の終了歌ID
     * @param kimariji     決まり字
     * @param color        色
     */
    fun start(fromKarutaId: KarutaIdentifier,
              toKarutaId: KarutaIdentifier,
              kimariji: Kimariji?,
              color: Color?) {
        start(karutaRepository.findIds(fromKarutaId, toKarutaId, color, kimariji))
    }

    /**
     * 力試しで過去に間違えた歌を練習対象にして練習を開始する.
     */
    fun startForExam() {
        start(karutaExamRepository.list().map { it.totalWrongKarutaIds })
    }

    /**
     * 次の問題を取り出す.
     */
    fun fetchNext() {
        karutaQuizRepository.first().subscribeNew({
            dispatcher.dispatch(OpenNextQuizAction(it.identifier()))
        }, {
            dispatcher.dispatch(OpenNextQuizAction(null))
        })
    }

    /**
     * 練習で間違えた歌を練習対象にして練習を再開する.
     */
    fun restartForPractice() {
        start(karutaQuizRepository.list().map { it.wrongKarutaIds })
    }

    /**
     * 練習結果を集計する.
     */
    fun aggregateResults() {
        karutaQuizRepository.list().subscribeNew({
            dispatcher.dispatch(AggregateResultsAction(TrainingResult(it.resultSummary())))
        }, {
            dispatcher.dispatch(AggregateResultsAction(null))
        })
    }

    private fun start(karutaIdsSingle: Single<KarutaIds>) {
        Single.zip<Karutas, KarutaIds, KarutaQuizzes>(karutaRepository.list(), karutaIdsSingle, BiFunction { karutas, karutaIds ->
            karutas.createQuizSet(karutaIds)
        }).flatMap {
            karutaQuizRepository.initialize(it).andThen(Single.just(it))
        }.subscribeNew({
            if (it.isEmpty) {
                dispatcher.dispatch(StartTrainingAction(null))
            } else {
                dispatcher.dispatch(StartTrainingAction(it.values.first().identifier()))
            }
        }, {
            dispatcher.dispatch(StartTrainingAction(null))
        })
    }
}
