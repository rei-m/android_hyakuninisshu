package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class TrainingResult implements ValueObject {

    private final KarutaQuizResultSummary resultSummary;

    public TrainingResult(@NonNull KarutaQuizResultSummary resultSummary) {
        this.resultSummary = resultSummary;
    }

    public int quizCount() {
        return resultSummary.quizCount();
    }

    public int correctCount() {
        return resultSummary.correctCount();
    }

    public float averageAnswerTime() {
        return resultSummary.averageAnswerTime();
    }

    public boolean canRestartTraining() {
        return resultSummary.correctCount() != resultSummary.quizCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingResult that = (TrainingResult) o;

        return resultSummary.equals(that.resultSummary);

    }

    @Override
    public int hashCode() {
        return resultSummary.hashCode();
    }

    @Override
    public String toString() {
        return "TrainingResult{" +
                "resultSummary=" + resultSummary +
                '}';
    }
}
