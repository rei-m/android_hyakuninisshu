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

public class KarutaQuizJudgement implements ValueObject {

    private final KarutaIdentifier karutaId;

    private final boolean isCorrect;

    public KarutaQuizJudgement(@NonNull KarutaIdentifier karutaId, boolean isCorrect) {
        this.karutaId = karutaId;
        this.isCorrect = isCorrect;
    }

    public KarutaIdentifier karutaId() {
        return karutaId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizJudgement that = (KarutaQuizJudgement) o;

        return isCorrect == that.isCorrect &&
                karutaId.equals(that.karutaId);
    }

    @Override
    public int hashCode() {
        int result = karutaId.hashCode();
        result = 31 * result + (isCorrect ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizJudgement{" +
                "karutaId=" + karutaId +
                ", isCorrect=" + isCorrect +
                "} " + super.toString();
    }
}
