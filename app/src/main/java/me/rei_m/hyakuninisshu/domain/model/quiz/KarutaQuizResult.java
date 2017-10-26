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

import me.rei_m.hyakuninisshu.domain.ValueObject;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

public class KarutaQuizResult implements ValueObject {

    private final KarutaQuizJudgement judgement;

    private final ChoiceNo choiceNo;

    private final long answerTime;

    KarutaQuizResult(@NonNull KarutaIdentifier correctKarutaId,
                     @NonNull ChoiceNo choiceNo,
                     boolean isCorrect,
                     long answerTime) {
        this.judgement = new KarutaQuizJudgement(correctKarutaId, isCorrect);
        this.choiceNo = choiceNo;
        this.answerTime = answerTime;
    }

    public KarutaIdentifier correctKarutaId() {
        return judgement.karutaId();
    }

    public ChoiceNo choiceNo() {
        return choiceNo;
    }

    public boolean isCorrect() {
        return judgement.isCorrect();
    }

    public long answerTime() {
        return answerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizResult result = (KarutaQuizResult) o;

        return answerTime == result.answerTime &&
                judgement.equals(result.judgement) &&
                choiceNo == result.choiceNo;
    }

    @Override
    public int hashCode() {
        int result = judgement.hashCode();
        result = 31 * result + choiceNo.hashCode();
        result = 31 * result + (int) (answerTime ^ (answerTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizResult{" +
                "judgement=" + judgement +
                ", choiceNo=" + choiceNo +
                ", answerTime=" + answerTime +
                '}';
    }
}
