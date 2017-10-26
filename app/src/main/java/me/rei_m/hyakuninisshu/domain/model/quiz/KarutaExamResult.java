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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.ValueObject;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;

public class KarutaExamResult implements ValueObject {

    private final KarutaQuizResultSummary resultSummary;

    private final KarutaIds wrongKarutaIds;

    private final List<KarutaQuizJudgement> judgements;

    public KarutaExamResult(@NonNull KarutaQuizResultSummary resultSummary,
                            @NonNull KarutaIds wrongKarutaIds) {
        this.resultSummary = resultSummary;
        this.wrongKarutaIds = wrongKarutaIds;
        this.judgements = new ArrayList<>();

        for (int i = 1; i <= Karuta.NUMBER_OF_KARUTA; i++) {
            KarutaIdentifier karutaId = new KarutaIdentifier(i);
            boolean isCorrect = !wrongKarutaIds.contains(karutaId);
            this.judgements.add(new KarutaQuizJudgement(karutaId, isCorrect));
        }
    }

    public int quizCount() {
        return resultSummary.quizCount();
    }

    public String score() {
        return resultSummary.correctCount() + "/" + resultSummary.quizCount();
    }

    public float averageAnswerTime() {
        return resultSummary.averageAnswerTime();
    }

    public KarutaIds wrongKarutaIds() {
        return wrongKarutaIds;
    }

    public List<KarutaQuizJudgement> judgements() {
        return Collections.unmodifiableList(judgements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaExamResult result = (KarutaExamResult) o;

        return resultSummary.equals(result.resultSummary) &&
                wrongKarutaIds.equals(result.wrongKarutaIds) &&
                judgements.equals(result.judgements);
    }

    @Override
    public int hashCode() {
        int result = resultSummary.hashCode();
        result = 31 * result + wrongKarutaIds.hashCode();
        result = 31 * result + judgements.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KarutaExamResult{" +
                "resultSummary=" + resultSummary +
                ", wrongKarutaIds=" + wrongKarutaIds +
                ", judgements=" + judgements +
                '}';
    }
}
