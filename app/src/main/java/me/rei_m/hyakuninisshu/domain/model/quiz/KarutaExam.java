package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class KarutaExam extends AbstractEntity<KarutaExam, KarutaExamIdentifier> {

    public static final int MAX_HISTORY_COUNT = 10;

    private final KarutaExamResult result;

    public KarutaExam(@NonNull KarutaExamIdentifier identifier,
                      @NonNull KarutaExamResult karutaExamResult) {
        super(identifier);
        this.result = karutaExamResult;
    }

    public KarutaExamResult result() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        KarutaExam that = (KarutaExam) o;

        return result.equals(that.result);

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + result.hashCode();
        return result1;
    }

    @Override
    public String toString() {
        return "KarutaExam{" +
                "result=" + result +
                "} " + super.toString();
    }
}
