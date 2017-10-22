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

import java.util.Collections;
import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

public class KarutaQuiz extends AbstractEntity<KarutaQuiz, KarutaQuizIdentifier> {

    public static final int CHOICE_SIZE = 4;

    private final List<KarutaIdentifier> choiceList;

    private final KarutaIdentifier correctId;

    private Date startDate;

    private KarutaQuizResult result;

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId) {
        super(identifier);
        this.choiceList = choiceList;
        this.correctId = correctId;
    }

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId,
                      @NonNull Date startDate) {
        this(identifier, choiceList, correctId);
        this.startDate = startDate;
    }

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier correctId,
                      @NonNull Date startDate,
                      long answerTime,
                      @NonNull ChoiceNo choiceNo,
                      boolean isCorrect) {
        this(identifier, choiceList, correctId, startDate);
        this.result = new KarutaQuizResult(correctId,
                choiceNo,
                isCorrect,
                answerTime);
    }

    public List<KarutaIdentifier> choiceList() {
        return Collections.unmodifiableList(choiceList);
    }

    public KarutaIdentifier correctId() {
        return correctId;
    }

    public Date startDate() {
        return startDate;
    }

    public KarutaQuizResult result() {
        return result;
    }

    public KarutaQuiz start(@NonNull Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public KarutaQuiz verify(@NonNull ChoiceNo choiceNo, @NonNull Date answerDate) throws IllegalStateException, IllegalArgumentException {
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
