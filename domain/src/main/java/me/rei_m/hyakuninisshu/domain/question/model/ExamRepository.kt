/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.domain.question.model

import java.util.Date

/**
 * 力試しのリポジトリ.
 */
interface ExamRepository {
    /**
     * 力試しを取得する.
     *
     * @param examId 力試しID
     *
     * @return 力試し
     */
    suspend fun findById(examId: ExamId): Exam?

    /**
     * 最新の力試しを取得する.
     *
     * @return 力試し
     */
    suspend fun last(): Exam?

    /**
     * 力試しコレクションを取得する.
     *
     * @return 力試しコレクション
     */
    suspend fun findCollection(): ExamCollection

    /**
     * 力試しを新規に永続化する(ここはちょっと雑).
     *
     * @param examResult 力試しの結果
     * @param tookExamDate 力試しを受けた日付
     *
     * @return 登録した力試しのID
     */
    suspend fun add(
        examResult: ExamResult,
        tookExamDate: Date,
    ): ExamId

    /**
     * 力試しを削除する.
     *
     * @param list 削除対象のリスト
     */
    suspend fun deleteList(list: List<Exam>)
}
