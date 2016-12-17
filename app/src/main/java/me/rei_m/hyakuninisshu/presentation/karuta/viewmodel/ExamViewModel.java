package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class ExamViewModel {

    public final boolean hasResult;

    public final String score;

    public final String averageAnswerTime;

    public ExamViewModel(boolean hasResult,
                         @NonNull String score,
                         @NonNull String averageAnswerTime) {
        this.hasResult = hasResult;
        this.score = score;
        this.averageAnswerTime = averageAnswerTime;
    }

    @Override
    public String toString() {
        return "ExamViewModel{" +
                "hasResult=" + hasResult +
                ", score='" + score + '\'' +
                ", averageAnswerTime='" + averageAnswerTime + '\'' +
                '}';
    }
}
