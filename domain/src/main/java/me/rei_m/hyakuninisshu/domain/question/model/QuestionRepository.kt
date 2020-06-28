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

package me.rei_m.hyakuninisshu.domain.question.model

interface QuestionRepository {
    /**
     * 問題を初期化する.トレーニングまたは力試しを開始する毎に初期化する.
     *
     * @param questionList 問題リスト
     */
    suspend fun initialize(questionList: List<Question>)

    /**
     * 問題の件数を取得する.
     *
     * @return 件数
     */
    suspend fun count(): Int

    /**
     * IDを指定して検索する.
     *
     * @param questionId 問題ID
     *
     * @return 指定されたIDの問題
     */
    suspend fun findById(questionId: QuestionId): Question?

    /**
     * 何番目の問題か指定して検索する
     *
     * @param no 問題の中で何番目か
     *
     * @return 指定された番号の問題ID
     */
    suspend fun findIdByNo(no: Int): QuestionId?

    /**
     * 問題の集合を取得する.
     *
     * @return 問題の集合
     */
    suspend fun findCollection(): QuestionCollection

    /**
     * 問題を永続化する.
     *
     * @param question 問題
     */
    suspend fun save(question: Question)
}
