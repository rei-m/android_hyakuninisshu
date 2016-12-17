package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class QuizResultViewModel {

    public final String score;

    public final String averageAnswerTime;

    public final boolean canRestartTraining;

    public QuizResultViewModel(@NonNull String score,
                               @NonNull String averageAnswerTime,
                               boolean canRestartTraining) {
        this.score = score;
        this.averageAnswerTime = averageAnswerTime;
        this.canRestartTraining = canRestartTraining;
    }

    @Override
    public String toString() {
        return "QuizResultViewModel{" +
                "score='" + score + '\'' +
                ", averageAnswerTime='" + averageAnswerTime + '\'' +
                ", canRestartTraining=" + canRestartTraining +
                '}';
    }
}
