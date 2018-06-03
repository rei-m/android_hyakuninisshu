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

package me.rei_m.hyakuninisshu.action.training

import javax.inject.Inject

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Function
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji
import me.rei_m.hyakuninisshu.domain.model.quiz.*

class TrainingActionDispatcher @Inject constructor(
        private val dispatcher: Dispatcher,
        private val karutaRepository: KarutaRepository,
        private val karutaQuizRepository: KarutaQuizRepository,
        private val karutaExamRepository: KarutaExamRepository
) {

    /**
     * 練習を初期化する.
     */
    fun initialize() {
        dispatcher.dispatch(InitializeTrainingAction())
    }

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
        karutaQuizRepository.first()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dispatcher.dispatch(OpenNextQuizAction(it.identifier()))
                }, {
                    // TODO: チェック処理
                    println(it)
                })
    }

    /**
     * 練習を終了する.
     */
    fun finish() {
        dispatcher.dispatch(FinishTrainingAction())
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
        karutaQuizRepository.list()
                .map<KarutaQuizzesResultSummary> { it.resultSummary() }
                .map<TrainingResult> { TrainingResult(it) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ trainingResult -> dispatcher.dispatch(AggregateResultsAction(trainingResult)) }) { throwable -> dispatcher.dispatch(AggregateResultsAction()) }
    }

    private fun start(karutaIdsSingle: Single<KarutaIds>) {
        Single.zip<Karutas, KarutaIds, KarutaQuizzes>(karutaRepository.list(), karutaIdsSingle, BiFunction<Karutas, KarutaIds, KarutaQuizzes> { obj, karutaIds -> obj.createQuizSet(karutaIds) })
                .flatMap { karutaQuizzes -> karutaQuizRepository.initialize(karutaQuizzes).andThen(Single.just(karutaQuizzes)) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ karutaQuizzes ->
                    if (karutaQuizzes.isEmpty) {
                        dispatcher.dispatch(StartTrainingAction(null))
                    } else {
                        // TODO: リファクタ
                        dispatcher.dispatch(StartTrainingAction(karutaQuizzes.values.first().identifier()))
                    }
                }
                ) { _ -> dispatcher.dispatch(StartTrainingAction()) }
    }
}
