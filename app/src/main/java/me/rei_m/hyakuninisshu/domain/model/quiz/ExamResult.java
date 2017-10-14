package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class ExamResult implements ValueObject {

    public final int quizCount;

    public final int collectCount;

    public final float averageAnswerTime;

    public final boolean[] resultList;

    public ExamResult(int quizCount,
                      int collectCount,
                      float averageAnswerTime,
                      @NonNull boolean[] resultList) {
        this.quizCount = quizCount;
        this.collectCount = collectCount;
        this.averageAnswerTime = averageAnswerTime;
        this.resultList = resultList;
    }
}
