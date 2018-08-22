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

import kotlinx.coroutines.experimental.launch
import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.*
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

@Singleton
class TrainingActionDispatcher @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val karutaQuizRepository: KarutaQuizRepository,
    private val karutaExamRepository: KarutaExamRepository,
    private val dispatcher: Dispatcher,
    private val coroutineContext: CoroutineContext
) {
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
        launch(coroutineContext) {
            start(karutaRepository.findIds(fromKarutaId, toKarutaId, color, kimariji))
        }
    }

    /**
     * 力試しで過去に間違えた歌を練習対象にして練習を開始する.
     */
    fun startForExam() {
        launch(coroutineContext) {
            start(karutaExamRepository.list().totalWrongKarutaIds)
        }
    }

    /**
     * 練習で間違えた歌を練習対象にして練習を再開する.
     */
    fun restartForPractice() {
        launch(coroutineContext) {
            start(karutaQuizRepository.list().wrongKarutaIds)
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
     * 練習結果を集計する.
     */
    fun aggregateResults() {
        launch(coroutineContext) {
            val quizzes = karutaQuizRepository.list()
            try {
                val resultSummary = quizzes.resultSummary()
                dispatcher.dispatch(AggregateResultsAction.createSuccess(TrainingResult(resultSummary)))
            } catch (e: IllegalStateException) {
                dispatcher.dispatch(AggregateResultsAction.createError(e))
            }
        }
    }

    private fun start(karutaIds: KarutaIds) {
        val karutas = karutaRepository.list()
        val quizSet = karutas.createQuizSet(karutaIds)
        karutaQuizRepository.initialize(quizSet)
        if (quizSet.isEmpty) {
            dispatcher.dispatch(StartTrainingAction(null))
        } else {
            dispatcher.dispatch(StartTrainingAction(quizSet.values.first().identifier()))
        }
    }
}
