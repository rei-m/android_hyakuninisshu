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

package me.rei_m.hyakuninisshu.action.exam

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import me.rei_m.hyakuninisshu.ext.scheduler
import me.rei_m.hyakuninisshu.util.rx.SchedulerProvider
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExamActionDispatcher @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val karutaQuizRepository: KarutaQuizRepository,
    private val karutaExamRepository: KarutaExamRepository,
    private val dispatcher: Dispatcher,
    private val schedulerProvider: SchedulerProvider
) {

    /**
     * 力試しを取得する.
     *
     * @param karutaExamId 力試しID
     */
    fun fetch(karutaExamId: KarutaExamIdentifier) {
        karutaExamRepository.findBy(karutaExamId).scheduler(schedulerProvider).subscribe({
            dispatcher.dispatch(FetchExamAction(it))
        }, {
            dispatcher.dispatch(FetchExamAction(null, it))
        })
    }

    /**
     * 最新の力試しを取得する.
     */
    fun fetchRecent() {
        karutaExamRepository.list().scheduler(schedulerProvider).subscribe({
            dispatcher.dispatch(FetchRecentExamAction(it.recent))
        }, {
            dispatcher.dispatch(FetchRecentExamAction(null, it))
        })
    }

    /**
     * 全ての力試しを取得する.
     */
    fun fetchAll() {
        karutaExamRepository.list().scheduler(schedulerProvider).subscribe({
            dispatcher.dispatch(FetchAllExamAction(it.all))
        }, {
            dispatcher.dispatch(FetchAllExamAction(null, it))
        })
    }

    /**
     * 力試しを開始する.
     */
    fun start() {
        Single.zip<Karutas, KarutaIds, KarutaQuizzes>(karutaRepository.list(), karutaRepository.findIds(), BiFunction { karutas, karutaIds ->
            karutas.createQuizSet(karutaIds)
        }).flatMap {
            karutaQuizRepository.initialize(it).andThen(Single.just(it))
        }.scheduler(schedulerProvider).subscribe({
            dispatcher.dispatch(StartExamAction(it.values.first().identifier()))
        }, {
            dispatcher.dispatch(StartExamAction(null, it))
        })
    }

    /**
     * 次の問題を取り出す.
     */
    fun fetchNext() {
        karutaQuizRepository.first().scheduler(schedulerProvider).subscribe({
            dispatcher.dispatch(OpenNextQuizAction(it.identifier()))
        }, {
            dispatcher.dispatch(OpenNextQuizAction(null, it))
        })
    }

    /**
     * 力試しを終了して結果を登録する.
     */
    fun finish() {
        karutaQuizRepository.list().flatMap {
            val result = KarutaExamResult(it.resultSummary(), it.wrongKarutaIds)
            karutaExamRepository.storeResult(result, Date())
        }.flatMap {
            karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT).andThen(karutaExamRepository.findBy(it))
        }.scheduler(schedulerProvider).subscribe({
            dispatcher.dispatch(FinishExamAction(it))
        }, {
            dispatcher.dispatch(FinishExamAction(null, it))
        })
    }
}
