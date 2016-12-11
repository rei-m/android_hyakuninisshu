package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class ExamResultViewModel {

    public final String result;

    public final String averageAnswerTime;

    public final boolean[] karutaQuizResultList;

    public ExamResultViewModel(@NonNull String result,
                               @NonNull String averageAnswerTime,
                               @NonNull boolean[] karutaQuizResultList) {
        this.result = result;
        this.averageAnswerTime = averageAnswerTime;
        this.karutaQuizResultList = karutaQuizResultList;
    }
}
