package me.rei_m.hyakuninisshu.domain.model.quiz;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizResultSummary implements ValueObject {

    private final int quizCount;

    private final int correctCount;

    private final float averageAnswerTime;

    public KarutaQuizResultSummary(int quizCount,
                                   int correctCount,
                                   float averageAnswerTime) {
        this.quizCount = quizCount;
        this.correctCount = correctCount;
        this.averageAnswerTime = averageAnswerTime;
    }

    public int quizCount() {
        return quizCount;
    }

    public int correctCount() {
        return correctCount;
    }

    public float averageAnswerTime() {
        return averageAnswerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizResultSummary that = (KarutaQuizResultSummary) o;

        if (quizCount != that.quizCount) return false;
        if (correctCount != that.correctCount) return false;
        return Float.compare(that.averageAnswerTime, averageAnswerTime) == 0;

    }

    @Override
    public int hashCode() {
        int result = quizCount;
        result = 31 * result + correctCount;
        result = 31 * result + (averageAnswerTime != +0.0f ? Float.floatToIntBits(averageAnswerTime) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizResultSummary{" +
                "quizCount=" + quizCount +
                ", correctCount=" + correctCount +
                ", averageAnswerTime=" + averageAnswerTime +
                '}';
    }
}
