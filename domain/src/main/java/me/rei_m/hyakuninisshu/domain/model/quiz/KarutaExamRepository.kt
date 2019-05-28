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
package me.rei_m.hyakuninisshu.domain.model.quiz

import java.util.Date

/**
 * 力試しリポジトリ.
 */
interface KarutaExamRepository {

    /**
     * 力試しの結果を永続化する.
     *
     * @param karutaExamResult 力試しの結果
     * @param tookExamDate 力試しを受けた日付
     * @return 登録した力試しのID
     */
    fun storeResult(karutaExamResult: KarutaExamResult, tookExamDate: Date): KarutaExamIdentifier

    /**
     * 力試しの受験履歴を調整する.
     *
     * @param historySize 調整する履歴数。指定された世代の履歴を保存し、古い受験履歴は削除する.
     */
    fun adjustHistory(historySize: Int)

    /**
     * 力試しを取得する.
     *
     * @param karutaExamId 力試しID
     * @return 力試し
     */
    fun findBy(karutaExamId: KarutaExamIdentifier): KarutaExam?

    /**
     * 力試しコレクションを取得する.
     *
     * @return 力試しコレクション
     */
    fun list(): KarutaExams
}
