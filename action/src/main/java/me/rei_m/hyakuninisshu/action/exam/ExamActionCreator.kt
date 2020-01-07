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
package me.rei_m.hyakuninisshu.action.exam

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExamActionCreator @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val karutaQuizRepository: KarutaQuizRepository,
    private val karutaExamRepository: KarutaExamRepository
) {

    /**
     * 力試しを取得する.
     *
     * @param karutaExamId 力試しID
     */
    fun fetch(karutaExamId: KarutaExamIdentifier): FetchExamAction = try {
        val karutaExam = karutaExamRepository.findBy(karutaExamId)
            ?: throw NoSuchElementException(karutaExamId.toString())
        FetchExamAction.createSuccess(karutaExam)
    } catch (e: Exception) {
        FetchExamAction.createError(e)
    }

    /**
     * 最新の力試しを取得する.
     */
    fun fetchRecent(): FetchRecentExamAction =
        try {
            val exams = karutaExamRepository.list()
            FetchRecentExamAction.createSuccess(exams.recent)
        } catch (e: Exception) {
            FetchRecentExamAction.createError(e)
        }

    /**
     * 全ての力試しを取得する.
     */
    fun fetchAll(): FetchAllExamAction =
        try {
            val exams = karutaExamRepository.list()
            FetchAllExamAction.createSuccess(exams.all)
        } catch (e: Exception) {
            FetchAllExamAction.createError(e)
        }

    /**
     * 力試しを開始する.
     */
    fun start(): StartExamAction = try {
        val karutas = karutaRepository.list()
        val targetIds = karutaRepository.findIds()
        val quizSet = karutas.createQuizSet(targetIds)
        karutaQuizRepository.initialize(quizSet)
        val firstQuizId = quizSet.values.first().identifier
        StartExamAction.createSuccess(firstQuizId)
    } catch (e: Exception) {
        StartExamAction.createError(e)
    }

    /**
     * 次の問題を取り出す.
     */
    fun fetchNext(): OpenNextQuizAction =
        try {
            val karutaQuiz = karutaQuizRepository.first()
                ?: throw NoSuchElementException("NextKarutaQuiz")
            OpenNextQuizAction.createSuccess(karutaQuiz.identifier)
        } catch (e: Exception) {
            OpenNextQuizAction.createError(e)
        }

    /**
     * 力試しを終了して結果を登録する.
     */
    fun finish(): FinishExamAction =
        try {
            val quizzes = karutaQuizRepository.list()
            val result = KarutaExamResult(quizzes.resultSummary(), quizzes.wrongKarutaIds)
            val storedExamId = karutaExamRepository.storeResult(result, Date())
            val exam = karutaExamRepository.findBy(storedExamId)
                ?: throw NoSuchElementException(storedExamId.toString())
            karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT)
            FinishExamAction.createSuccess(exam)
        } catch (e: Exception) {
            FinishExamAction.createError(e)
        }
}
