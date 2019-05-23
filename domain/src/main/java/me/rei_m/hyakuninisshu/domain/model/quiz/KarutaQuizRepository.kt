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

/**
 * 問題リポジトリ.
 */
interface KarutaQuizRepository {

    /**
     * 問題を初期化する.トレーニングまたは力試しを開始する毎に初期化する.
     *
     * @param karutaQuizzes 問題コレクション
     */
    fun initialize(karutaQuizzes: KarutaQuizzes)

    /**
     * 先頭の問題を取得する.
     *
     * @return 問題
     */
    fun first(): KarutaQuiz?

    /**
     * 指定した問題を取得する.
     *
     * @param identifier 問題ID
     * @return 問題
     */
    fun findBy(identifier: KarutaQuizIdentifier): KarutaQuiz?

    /**
     * 問題を保存する.
     *
     * @param karutaQuiz 問題
     * @return Completable
     */
    fun store(karutaQuiz: KarutaQuiz)

    /**
     * 問題のコレクションを取得する.
     *
     * @return 問題コレクション
     */
    fun list(): KarutaQuizzes

    /**
     * 解答済みの問題をカウントする.
     *
     * @return 問題カウンター
     */
    fun countQuizByAnswered(): KarutaQuizCounter
}