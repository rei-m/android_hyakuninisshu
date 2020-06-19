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

package me.rei_m.hyakuninisshu.state.exam.action

import android.content.Context
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaRepository
import me.rei_m.hyakuninisshu.domain.question.model.ExamId
import me.rei_m.hyakuninisshu.domain.question.model.ExamRepository
import me.rei_m.hyakuninisshu.domain.question.model.Question
import me.rei_m.hyakuninisshu.domain.question.model.QuestionRepository
import me.rei_m.hyakuninisshu.domain.question.service.CreateQuestionListService
import me.rei_m.hyakuninisshu.state.R
import me.rei_m.hyakuninisshu.state.core.ext.diffString
import me.rei_m.hyakuninisshu.state.core.ext.toMaterial
import me.rei_m.hyakuninisshu.state.core.ext.toResult
import me.rei_m.hyakuninisshu.state.core.ext.toText
import me.rei_m.hyakuninisshu.state.exam.model.ExamResult
import me.rei_m.hyakuninisshu.state.question.model.QuestionResult
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import me.rei_m.hyakuninisshu.domain.question.model.ExamResult as DomainExamResult

@Singleton
class ExamActionCreator @Inject constructor(
    private val context: Context,
    private val karutaRepository: KarutaRepository,
    private val questionRepository: QuestionRepository,
    private val examRepository: ExamRepository,
    private val createQuestionListService: CreateQuestionListService
) {
    /**
     * 力試しを開始する.
     *
     * @return StartExamAction
     */
    suspend fun start() = try {
        val allKarutaNoCollection = KarutaNoCollection(KarutaNo.LIST)

        val targetKarutaList = karutaRepository.findAll()

        val targetKarutaNoCollection = KarutaNoCollection(targetKarutaList.map { it.no })

        val questionList = createQuestionListService(
            allKarutaNoCollection,
            targetKarutaNoCollection,
            Question.CHOICE_SIZE
        )

        questionRepository.initialize(questionList)

        StartExamAction.Success(questionList.first().identifier.value)
    } catch (e: Exception) {
        StartExamAction.Failure(e)
    }

    /**
     * 力試しを終了して結果を登録する.
     *
     * @param now 力試しを終了した時間
     *
     * @return FinishExamAction
     */
    suspend fun finish(
        now: Date = Date()
    ) = try {
        val questionCollection = questionRepository.findCollection()
        val result = DomainExamResult(
            questionCollection.resultSummary,
            questionCollection.wrongKarutaNoCollection
        )
        val addedExamId = examRepository.add(result, now)
        val examCollection = examRepository.findCollection()
        examRepository.deleteList(examCollection.overflowed)

        val averageAnswerTimeString = String.format(
            Locale.JAPAN,
            "%.2f",
            result.resultSummary.averageAnswerSec
        )

        FinishExamAction.Success(
            examResult = ExamResult(
                id = addedExamId.value,
                score = result.resultSummary.score,
                averageAnswerSecText = context.getString(R.string.seconds, averageAnswerTimeString),
                questionResultList = KarutaNo.LIST.map { karutaNo ->
                    QuestionResult(
                        karutaNo = karutaNo.value,
                        karutaNoText = karutaNo.toText(context),
                        isCorrect = !result.wrongKarutaNoCollection.contains(karutaNo)
                    )
                },
                fromNowText = now.diffString(context, now)
            )
        )
    } catch (e: Exception) {
        FinishExamAction.Failure(e)
    }

    /**
     * 最新の力試しの結果を取得する.
     *
     * @param now 現在時刻
     *
     * @return FetchRecentExamAction
     */
    suspend fun fetchRecentResult(
        now: Date = Date()
    ): FetchRecentExamResultAction {
        try {
            val recentExam =
                examRepository.last() ?: return FetchRecentExamResultAction.Success(null)
            return FetchRecentExamResultAction.Success(recentExam.toResult(context, now))
        } catch (e: Exception) {
            return FetchRecentExamResultAction.Failure(e)
        }
    }

    /**
     * 指定した力試しの結果を取得する.
     *
     * @param id 力試しID
     * @param now 現在時刻
     *
     * @return FetchExamResultAction
     */
    suspend fun fetchResult(
        id: Long,
        now: Date = Date()
    ): FetchExamResultAction {
        try {
            val exam = examRepository.findById(ExamId(id))
                ?: return FetchExamResultAction.Failure(NoSuchElementException())
            return FetchExamResultAction.Success(
                examResult = exam.toResult(context, now),
                materialList = karutaRepository.findAll()
                    .map { it.toMaterial(context) }
            )
        } catch (e: Exception) {
            return FetchExamResultAction.Failure(e)
        }
    }

    /**
     * すべての力試しの結果を取得する.
     *
     * @param now 現在時刻
     *
     * @return FetchAllExamResultAction
     */
    suspend fun fetchAllResult(
        now: Date = Date()
    ): FetchAllExamResultAction = try {
        val examCollection = examRepository.findCollection()
        FetchAllExamResultAction.Success(
            examResultList = examCollection.all.map { it.toResult(context, now) }
        )
    } catch (e: Exception) {
        FetchAllExamResultAction.Failure(e)
    }
}
