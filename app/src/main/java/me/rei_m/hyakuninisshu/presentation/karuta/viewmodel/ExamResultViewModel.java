package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class ExamResultViewModel {

    public final String score;

    public final String averageAnswerTime;

    public final boolean[] karutaQuizResultList;

    public ExamResultViewModel(@NonNull String score,
                               @NonNull String averageAnswerTime,
                               @NonNull boolean[] karutaQuizResultList) {
        this.score = score;
        this.averageAnswerTime = averageAnswerTime;
        this.karutaQuizResultList = karutaQuizResultList;
    }
}
