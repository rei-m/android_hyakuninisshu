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
import me.rei_m.hyakuninisshu.ext.SingleExt
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExamActionDispatcher @Inject constructor(
        private val dispatcher: Dispatcher,
        private val karutaRepository: KarutaRepository,
        private val karutaQuizRepository: KarutaQuizRepository,
        private val karutaExamRepository: KarutaExamRepository
) : SingleExt {

    /**
     * 力試しを取得する.
     *
     * @param karutaExamId 力試しID
     */
    fun fetch(karutaExamId: KarutaExamIdentifier) {
        karutaExamRepository.findBy(karutaExamId).subscribeNew({
            dispatcher.dispatch(FetchExamAction(it))
        }, {
            dispatcher.dispatch(FetchExamAction(null))
        })
    }

    /**
     * 最新の力試しを取得する.
     */
    fun fetchRecent() {
        karutaExamRepository.list().subscribeNew { karutaExams ->
            karutaExams.recent?.let { dispatcher.dispatch(FetchRecentExamAction(it)) }
        }
    }

    /**
     * 力試しを開始する.
     */
    fun start() {
        Single.zip<Karutas, KarutaIds, KarutaQuizzes>(karutaRepository.list(), karutaRepository.findIds(), BiFunction { karutas, karutaIds ->
            karutas.createQuizSet(karutaIds)
        }).flatMap {
            karutaQuizRepository.initialize(it).andThen(Single.just(it))
        }.subscribeNew {
            dispatcher.dispatch(StartExamAction(it.values.first().identifier()))
        }
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
     * 力試しを終了して結果を登録する.
     */
    fun finish() {
        // TODO: 未解答の問題があった場合エラー
        karutaQuizRepository.list().flatMap {
            val result = KarutaExamResult(it.resultSummary(), it.wrongKarutaIds)
            karutaExamRepository.storeResult(result, Date())
        }.flatMap {
            karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT).andThen(karutaExamRepository.findBy(it))
        }.subscribeNew {
            dispatcher.dispatch(FinishExamAction(it))
        }
    }
}
