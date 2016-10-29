package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class QuizResultViewModel {

    public final String result;

    public final String averageAnswerTime;

    public QuizResultViewModel(@NonNull String result,
                               @NonNull String averageAnswerTime) {
        this.result = result;
        this.averageAnswerTime = averageAnswerTime;
    }

    @Override
    public String toString() {
        return "QuizResultViewModel{" +
                "result='" + result + '\'' +
                ", averageAnswerTime='" + averageAnswerTime + '\'' +
                '}';
    }
}
