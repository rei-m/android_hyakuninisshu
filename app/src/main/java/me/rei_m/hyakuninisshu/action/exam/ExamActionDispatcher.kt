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
package me.rei_m.hyakuninisshu.action.exam

import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import me.rei_m.hyakuninisshu.util.launchAction
import java.util.Date
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
        launchAction(coroutineContext, {
            val karutaExam = karutaExamRepository.findBy(karutaExamId)
                ?: throw NoSuchElementException(karutaExamId.toString())
            dispatcher.dispatch(FetchExamAction.createSuccess(karutaExam))
        }, {
            dispatcher.dispatch(FetchExamAction.createError(it))
        })
    }

    /**
     * 最新の力試しを取得する.
     */
    fun fetchRecent() {
        launchAction(coroutineContext, {
            val exams = karutaExamRepository.list()
            dispatcher.dispatch(FetchRecentExamAction.createSuccess(exams.recent))
        }, {
            dispatcher.dispatch(FetchRecentExamAction.createError(it))
        })
    }

    /**
     * 全ての力試しを取得する.
     */
    fun fetchAll() {
        launchAction(coroutineContext, {
            val exams = karutaExamRepository.list()
            dispatcher.dispatch(FetchAllExamAction.createSuccess(exams.all))
        }, {
            dispatcher.dispatch(FetchAllExamAction.createError(it))
        })
    }

    /**
     * 力試しを開始する.
     */
    fun start() {
        launchAction(coroutineContext, {
            val karutas = karutaRepository.list()
            val targetIds = karutaRepository.findIds()
            val quizSet = karutas.createQuizSet(targetIds)
            karutaQuizRepository.initialize(quizSet)
            val firstQuizId = quizSet.values.first().identifier
            dispatcher.dispatch(StartExamAction.createSuccess(firstQuizId))
        }, {
            dispatcher.dispatch(StartExamAction.createError(it))
        })
    }

    /**
     * 次の問題を取り出す.
     */
    fun fetchNext() {
        launchAction(coroutineContext, {
            val karutaQuiz = karutaQuizRepository.first() ?: throw NoSuchElementException("NextKarutaQuiz")
            dispatcher.dispatch(OpenNextQuizAction.createSuccess(karutaQuiz.identifier))
        }, {
            dispatcher.dispatch(OpenNextQuizAction.createError(it))
        })
    }

    /**
     * 力試しを終了して結果を登録する.
     */
    fun finish() {
        launchAction(coroutineContext, {
            val quizzes = karutaQuizRepository.list()
            val result = KarutaExamResult(quizzes.resultSummary(), quizzes.wrongKarutaIds)
            val storedExamId = karutaExamRepository.storeResult(result, Date())
            val exam = karutaExamRepository.findBy(storedExamId)
                ?: throw NoSuchElementException(storedExamId.toString())
            karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT)
            dispatcher.dispatch(FinishExamAction.createSuccess(exam))
        }, {
            dispatcher.dispatch(FinishExamAction.createError(it))
        })
    }
}
