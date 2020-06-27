/*
 * Copyright (c) 2020. Rei Matsushita
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

package me.rei_m.hyakuninisshu.state.training.action

import android.content.Context
import me.rei_m.hyakuninisshu.domain.karuta.model.*
import me.rei_m.hyakuninisshu.domain.question.model.ExamRepository
import me.rei_m.hyakuninisshu.domain.question.model.Question
import me.rei_m.hyakuninisshu.domain.question.model.QuestionRepository
import me.rei_m.hyakuninisshu.domain.question.service.CreateQuestionListService
import me.rei_m.hyakuninisshu.state.R
import me.rei_m.hyakuninisshu.state.training.model.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingActionCreator @Inject constructor(
    private val context: Context,
    private val karutaRepository: KarutaRepository,
    private val questionRepository: QuestionRepository,
    private val examRepository: ExamRepository,
    private val createQuestionListService: CreateQuestionListService
) {
    /**
     * 条件を指定して練習を開始する.
     *
     * @param fromCondition 歌番号の開始
     * @param toCondition 歌番号の終了
     * @param kimariji 決まり字
     * @param color 色
     *
     * @return StartTrainingAction
     */
    suspend fun start(
        fromCondition: RangeFromCondition,
        toCondition: RangeToCondition,
        kimariji: KimarijiCondition,
        color: ColorCondition
    ) = try {
        val targetKarutaList = karutaRepository.findAllWithCondition(
            fromNo = fromCondition.value,
            toNo = toCondition.value,
            kimarijis = if (kimariji.value != null) listOf(kimariji.value) else Kimariji.values()
                .toList(),
            colors = if (color.value != null) listOf(color.value) else KarutaColor.values().toList()
        )

        if (targetKarutaList.isEmpty()) {
            StartTrainingAction.Empty()
        } else {
            start(KarutaNoCollection(targetKarutaList.map { it.no }))
        }
    } catch (e: Exception) {
        StartTrainingAction.Failure(e)
    }

    /**
     * 過去の力試しの結果から間違えた問題を抽出して練習を開始する.
     */
    suspend fun startByExamPractice(): StartTrainingAction = try {
        val totalWrongKarutaNoCollection =
            examRepository.findCollection().totalWrongKarutaNoCollection
        if (totalWrongKarutaNoCollection.size == 0) {
            StartTrainingAction.Empty()
        } else {
            start(totalWrongKarutaNoCollection)
        }
    } catch (e: Exception) {
        StartTrainingAction.Failure(e)
    }

    /**
     * 練習で間違えた問題を抽出して練習を開始する.
     */
    suspend fun restart(): StartTrainingAction = try {
        val targetKarutaNoList = questionRepository
            .findCollection()
            .wrongKarutaNoCollection.asRandomized

        if (targetKarutaNoList.isEmpty()) {
            StartTrainingAction.Empty()
        } else {
            start(KarutaNoCollection(targetKarutaNoList))
        }
    } catch (e: Exception) {
        StartTrainingAction.Failure(e)
    }

    /**
     * 練習の回答結果を集計する
     */
    suspend fun aggregateResults(): AggregateResultsAction {
        try {
            val questionCollection = questionRepository.findCollection()
            val resultSummary = questionCollection.resultSummary
            val averageAnswerTimeString = String.format(
                Locale.JAPAN,
                "%.2f",
                resultSummary.averageAnswerSec
            )
            val trainingResult = TrainingResult(
                score = resultSummary.score,
                averageAnswerSecText = context.getString(R.string.seconds, averageAnswerTimeString),
                canRestart = 0 < questionCollection.wrongKarutaNoCollection.size
            )
            return AggregateResultsAction.Success(trainingResult)
        } catch (e: Exception) {
            return AggregateResultsAction.Failure(e)
        }
    }

    private suspend fun start(targetKarutaNoCollection: KarutaNoCollection): StartTrainingAction {

        val allKarutaNoCollection = KarutaNoCollection(KarutaNo.LIST)

        val questionList = createQuestionListService(
            allKarutaNoCollection,
            targetKarutaNoCollection,
            Question.CHOICE_SIZE
        )

        questionRepository.initialize(questionList)

        return StartTrainingAction.Success(questionList.first().identifier.value)
    }
}
