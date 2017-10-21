package me.rei_m.hyakuninisshu.domain.model.quiz;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizCounter implements ValueObject {

    private final int totalCount;

    private final int answeredCount;

    public KarutaQuizCounter(int totalCount, int answeredCount) {
        this.totalCount = totalCount;
        this.answeredCount = answeredCount;
    }

    public String value() {
        return String.valueOf(answeredCount + 1) + " / " + String.valueOf(totalCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizCounter that = (KarutaQuizCounter) o;

        if (totalCount != that.totalCount) return false;

        return answeredCount == that.answeredCount;
    }

    @Override
    public int hashCode() {
        int result = totalCount;
        result = 31 * result + answeredCount;
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizCounter{" +
                "totalCount=" + totalCount +
                ", answeredCount=" + answeredCount +
                '}';
    }
}
