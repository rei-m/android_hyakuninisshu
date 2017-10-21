package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import java.util.Arrays;

import me.rei_m.hyakuninisshu.domain.ValueObject;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;

public class KarutaExamResult implements ValueObject {

    private final KarutaQuizResultSummary resultSummary;

    private final KarutaIds wrongKarutaIds;

    private final boolean[] resultList;

    public KarutaExamResult(@NonNull KarutaQuizResultSummary resultSummary,
                            @NonNull KarutaIds wrongKarutaIds) {
        this.resultSummary = resultSummary;
        this.wrongKarutaIds = wrongKarutaIds;

        final boolean[] karutaQuizResultList = new boolean[Karuta.NUMBER_OF_KARUTA];

        Arrays.fill(karutaQuizResultList, true);

        for (KarutaIdentifier wrongKarutaIdentifier : wrongKarutaIds.asList()) {
            int wrongKarutaIndex = (int) wrongKarutaIdentifier.value() - 1;
            karutaQuizResultList[wrongKarutaIndex] = false;
        }

        this.resultList = karutaQuizResultList;
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

    public boolean[] resultList() {
        return resultList.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaExamResult result = (KarutaExamResult) o;

        if (!resultSummary.equals(result.resultSummary)) return false;
        if (!wrongKarutaIds.equals(result.wrongKarutaIds)) return false;
        return Arrays.equals(resultList, result.resultList);

    }

    @Override
    public int hashCode() {
        int result = resultSummary.hashCode();
        result = 31 * result + wrongKarutaIds.hashCode();
        result = 31 * result + Arrays.hashCode(resultList);
        return result;
    }

    @Override
    public String toString() {
        return "KarutaExamResult{" +
                "resultSummary=" + resultSummary +
                ", wrongKarutaIds=" + wrongKarutaIds +
                ", resultList=" + Arrays.toString(resultList) +
                '}';
    }
}
