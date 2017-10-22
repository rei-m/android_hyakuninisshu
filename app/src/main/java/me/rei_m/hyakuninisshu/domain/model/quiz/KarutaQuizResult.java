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

    private final KarutaIdentifier collectKarutaId;

    private final ChoiceNo choiceNo;

    private final boolean isCorrect;

    private final long answerTime;

    KarutaQuizResult(@NonNull KarutaIdentifier collectKarutaId,
                     @NonNull ChoiceNo choiceNo,
                     boolean isCorrect,
                     long answerTime) {
        this.collectKarutaId = collectKarutaId;
        this.choiceNo = choiceNo;
        this.isCorrect = isCorrect;
        this.answerTime = answerTime;
    }

    public KarutaIdentifier collectKarutaId() {
        return collectKarutaId;
    }

    public ChoiceNo choiceNo() {
        return choiceNo;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public long answerTime() {
        return answerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizResult result = (KarutaQuizResult) o;

        if (isCorrect != result.isCorrect) return false;
        if (answerTime != result.answerTime) return false;
        if (!collectKarutaId.equals(result.collectKarutaId)) return false;
        return choiceNo == result.choiceNo;

    }

    @Override
    public int hashCode() {
        int result = collectKarutaId.hashCode();
        result = 31 * result + choiceNo.hashCode();
        result = 31 * result + (isCorrect ? 1 : 0);
        result = 31 * result + (int) (answerTime ^ (answerTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizResult{" +
                "collectKarutaId=" + collectKarutaId +
                ", choiceNo=" + choiceNo +
                ", isCorrect=" + isCorrect +
                ", answerTime=" + answerTime +
                '}';
    }
}
