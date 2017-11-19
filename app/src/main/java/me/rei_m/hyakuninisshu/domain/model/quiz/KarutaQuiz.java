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
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

/**
 * 百人一首の問題.
 */
public class KarutaQuiz extends AbstractEntity<KarutaQuiz, KarutaQuizIdentifier> {

    private final List<KarutaIdentifier> choiceList;

    private final KarutaIdentifier correctId;

    private Date startDate;

    private KarutaQuizResult result;

    /**
     * 問題作成時の状態.
     *
     * @param identifier    問題ID
     * @param choiceList    選択肢
     * @param correctId     正解の歌ID
     */
    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId) {
        super(identifier);
        this.choiceList = choiceList;
        this.correctId = correctId;
    }

    /**
     * 問題解答開始時の状態.
     *
     * @param identifier    問題ID
     * @param choiceList    選択肢
     * @param correctId     正解の歌ID
     * @param startDate     解答開始時間
     */
    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId,
                      @NonNull Date startDate) {
        this(identifier, choiceList, correctId);
        this.startDate = startDate;
    }

    /**
     * 解答完了時の状態.
     *
     * @param identifier    問題ID
     * @param choiceList    選択肢
     * @param correctId     正解の歌ID
     * @param startDate     解答開始時間
     * @param answerMillSec    解答時間
     * @param choiceNo      選択した選択肢
     * @param isCorrect     正誤
     */
    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId,
                      @NonNull Date startDate,
                      long answerMillSec,
                      @NonNull ChoiceNo choiceNo,
                      boolean isCorrect) {
        this(identifier, choiceList, correctId, startDate);
        this.result = new KarutaQuizResult(correctId,
                choiceNo,
                isCorrect,
                answerMillSec);
    }

    /**
     * @return 選択肢
     */
    public List<KarutaIdentifier> choiceList() {
        return Collections.unmodifiableList(choiceList);
    }

    /**
     * @return 正解の歌ID
     */
    public KarutaIdentifier correctId() {
        return correctId;
    }

    /**
     * @return 解答開始時間
     */
    @Nullable
    public Date startDate() {
        return startDate;
    }

    /**
     * @return 解答結果. 未解答の場合はNullが返る
     */
    @Nullable
    public KarutaQuizResult result() {
        return result;
    }

    /**
     * 解答を開始する.
     *
     * @param startDate 解答開始時間
     * @return 問題
     */
    public KarutaQuiz start(@NonNull Date startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * 選択肢が正解か判定する.
     *
     * @param choiceNo      選択肢番号
     * @param answerDate    解答した時間
     * @return              解答後の問題
     * @throws IllegalStateException    解答開始していない場合
     */
    public KarutaQuiz verify(@NonNull ChoiceNo choiceNo, @NonNull Date answerDate) throws IllegalStateException {
        if (startDate == null) {
            throw new IllegalStateException("Quiz is not started. Call start.");
        }
        KarutaIdentifier selectedId = choiceList.get(choiceNo.asIndex());
        boolean isCorrect = correctId.equals(selectedId);
        long answerTime = answerDate.getTime() - startDate.getTime();
        this.result = new KarutaQuizResult(correctId, choiceNo, isCorrect, answerTime);
        return this;
    }

    @Override
    public String toString() {
        return "KarutaQuiz{" +
                "choiceList=" + choiceList +
                ", correctId=" + correctId +
                ", startDate=" + startDate +
                ", result=" + result +
                "} " + super.toString();
    }
}
