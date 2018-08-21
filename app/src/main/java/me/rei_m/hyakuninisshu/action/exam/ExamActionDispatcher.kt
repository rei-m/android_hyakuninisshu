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

import kotlinx.coroutines.experimental.launch
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

@Singleton
class ExamActionDispatcher @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val karutaQuizRepository: KarutaQuizRepository,
    private val karutaExamRepository: KarutaExamRepository,
    private val dispatcher: Dispatcher,
    private val coroutineContext: CoroutineContext
) {

    /**
     * 力試しを取得する.
     *
     * @param karutaExamId 力試しID
     */
    fun fetch(karutaExamId: KarutaExamIdentifier) {
        launch(coroutineContext) {
            val action = karutaExamRepository.findBy(karutaExamId)?.let {
                FetchExamAction.createSuccess(it)
            } ?: FetchExamAction.createError(NoSuchElementException(karutaExamId.toString()))
            dispatcher.dispatch(action)
        }
    }

    /**
     * 最新の力試しを取得する.
     */
    fun fetchRecent() {
        launch(coroutineContext) {
            val exams = karutaExamRepository.list()
            dispatcher.dispatch(FetchRecentExamAction.createSuccess(exams.recent))
        }
    }

    /**
     * 全ての力試しを取得する.
     */
    fun fetchAll() {
        launch(coroutineContext) {
            val exams = karutaExamRepository.list()
            dispatcher.dispatch(FetchAllExamAction.createSuccess(exams.all))
        }
    }

    /**
     * 力試しを開始する.
     */
    fun start() {
        launch(coroutineContext) {
            val karutas = karutaRepository.list()
            val targetIds = karutaRepository.findIds()
            val quizSet = karutas.createQuizSet(targetIds)
            karutaQuizRepository.initialize(quizSet)
            dispatcher.dispatch(StartExamAction(quizSet.values.first().identifier()))
        }
    }

    /**
     * 次の問題を取り出す.
     */
    fun fetchNext() {
        launch(coroutineContext) {
            val action = karutaQuizRepository.first()?.let {
                OpenNextQuizAction.createSuccess(it.identifier())
            } ?: OpenNextQuizAction.createError(NoSuchElementException("KarutaQuiz"))
            dispatcher.dispatch(action)
        }
    }

    /**
     * 力試しを終了して結果を登録する.
     */
    fun finish() {
        launch(coroutineContext) {
            val quizzes = karutaQuizRepository.list()
            val result = KarutaExamResult(quizzes.resultSummary(), quizzes.wrongKarutaIds)
            val storedExamId = karutaExamRepository.storeResult(result, Date())
            val exam = karutaExamRepository.findBy(storedExamId)
            karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT)

            dispatcher.dispatch(FinishExamAction(exam))
        }
    }
}
