/*
 * Copyright (c) 2019. Rei Matsushita
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
package me.rei_m.hyakuninisshu.action.training

import me.rei_m.hyakuninisshu.action.Dispatcher
import me.rei_m.hyakuninisshu.domain.model.karuta.Color
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingActionCreator @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val karutaQuizRepository: KarutaQuizRepository,
    private val karutaExamRepository: KarutaExamRepository,
    private val dispatcher: Dispatcher
) {
    /**
     * 練習を開始する.
     *
     * @param fromKarutaId 練習範囲の開始歌ID
     * @param toKarutaId 練習範囲の終了歌ID
     * @param kimariji 決まり字
     * @param color 色
     */
    fun start(
        fromKarutaId: KarutaIdentifier,
        toKarutaId: KarutaIdentifier,
        kimariji: Kimariji?,
        color: Color?
    ) {
        try {
            start(karutaRepository.findIds(fromKarutaId, toKarutaId, color, kimariji))
        } catch (e: Exception) {
            dispatcher.dispatch(StartTrainingAction.createError(e))
        }
    }

    /**
     * 力試しで過去に間違えた歌を練習対象にして練習を開始する.
     */
    fun startForExam() {
        try {
            start(karutaExamRepository.list().totalWrongKarutaIds)
        } catch (e: Exception) {
            dispatcher.dispatch(StartTrainingAction.createError(e))
        }
    }

    /**
     * 練習で間違えた歌を練習対象にして練習を再開する.
     */
    fun restartForPractice() {
        try {
            start(karutaQuizRepository.list().wrongKarutaIds)
        } catch (e: Exception) {
            dispatcher.dispatch(StartTrainingAction.createError(e))
        }
    }

    /**
     * 次の問題を取り出す.
     */
    fun fetchNext() {
        try {
            val karutaQuiz = karutaQuizRepository.first()
                ?: throw NoSuchElementException("NextKarutaQuiz")
            dispatcher.dispatch(OpenNextQuizAction.createSuccess(karutaQuiz.identifier))
        } catch (e: Exception) {
            dispatcher.dispatch(OpenNextQuizAction.createError(e))
        }
    }

    /**
     * 練習結果を集計する.
     */
    fun aggregateResults() {
        try {
            val quizzes = karutaQuizRepository.list()
            val resultSummary = quizzes.resultSummary()
            dispatcher.dispatch(AggregateResultsAction.createSuccess(TrainingResult(resultSummary)))
        } catch (e: Exception) {
            dispatcher.dispatch(AggregateResultsAction.createError(e))
        }
    }

    private fun start(karutaIds: KarutaIds) {
        val karutas = karutaRepository.list()
        val quizSet = karutas.createQuizSet(karutaIds)
        karutaQuizRepository.initialize(quizSet)
        if (quizSet.isEmpty) {
            dispatcher.dispatch(StartTrainingAction.createSuccess(null))
        } else {
            dispatcher.dispatch(StartTrainingAction.createSuccess(quizSet.values.first().identifier))
        }
    }
}
