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

package me.rei_m.hyakuninisshu.action.exam

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import java.util.*
import javax.inject.Inject

class ExamActionDispatcher @Inject constructor(
        private val dispatcher: Dispatcher,
        private val karutaRepository: KarutaRepository,
        private val karutaQuizRepository: KarutaQuizRepository,
        private val karutaExamRepository: KarutaExamRepository
) {

    /**
     * 力試しを取得する.
     *
     * @param karutaExamIdentifier 力試しID
     */
    fun fetch(karutaExamIdentifier: KarutaExamIdentifier) {
        karutaExamRepository.findBy(karutaExamIdentifier)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { karutaExam -> dispatcher.dispatch(FetchExamAction(karutaExam)) }
    }

    /**
     * 最新の力試しを取得する.
     */
    fun fetchRecent() {
        karutaExamRepository.list()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { karutaExams ->
                    karutaExams.recent?.let { dispatcher.dispatch(FetchRecentExamAction(it)) }
                }
    }

    /**
     * 力試しを開始する.
     */
    fun start() {
        Single.zip<Karutas, KarutaIds, KarutaQuizzes>(karutaRepository.list(), karutaRepository.findIds(), BiFunction<Karutas, KarutaIds, KarutaQuizzes> { obj, karutaIds -> obj.createQuizSet(karutaIds) })
                .flatMap { karutaQuizRepository.initialize(it).andThen(Single.just(it)) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { karutaQuizzes -> dispatcher.dispatch(StartExamAction(karutaQuizzes.values.first().identifier())) }
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
     * 力試しを終了して結果を登録する.
     */
    fun finish() {
        // TODO: 未解答の問題があった場合エラー
        karutaQuizRepository.list()
                .map { karutaQuizzes -> KarutaExamResult(karutaQuizzes.resultSummary(), karutaQuizzes.wrongKarutaIds) }
                .flatMap { karutaExamResult -> karutaExamRepository.storeResult(karutaExamResult, Date()) }
                .flatMap { karutaExamIdentifier -> karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT).andThen(karutaExamRepository.findBy(karutaExamIdentifier)) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { karutaExam -> dispatcher.dispatch(FinishExamAction(karutaExam)) }
    }
}
