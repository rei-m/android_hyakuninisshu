/*
 * Copyright (c) 2017. Rei Matsushita
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

package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * 力試しリポジトリ.
 */
public interface KarutaExamRepository {

    /**
     * 力試しの結果を永続化する.
     *
     * @param karutaExamResult 力試しの結果
     * @param tookExamDate     力試しを受けた日付
     * @return 登録した力試しのID
     */
    Single<KarutaExamIdentifier> storeResult(@NonNull KarutaExamResult karutaExamResult, @NonNull Date tookExamDate);

    /**
     * 力試しの受験履歴を調整する.
     *
     * @param historySize 調整する履歴数。指定された世代の履歴を保存し、古い受験履歴は削除する.
     * @return Completable
     */
    Completable adjustHistory(int historySize);

    /**
     * 力試しを取得する.
     *
     * @param identifier 力試しID
     * @return 力試し
     */
    Single<KarutaExam> findBy(@NonNull KarutaExamIdentifier identifier);

    /**
     * 力試しコレクションを取得する.
     *
     * @return 力試しコレクション
     */
    Single<KarutaExams> list();
}
