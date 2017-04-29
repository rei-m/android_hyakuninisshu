package me.rei_m.hyakuninisshu.domain.karuta.model;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class TrainingResult implements ValueObject {

    public final int quizCount;

    public final int collectCount;

    public final float averageAnswerTime;

    public final boolean canRestartTraining;

    public TrainingResult(int quizCount, int collectCount, float averageAnswerTime, boolean canRestartTraining) {
        this.quizCount = quizCount;
        this.collectCount = collectCount;
        this.averageAnswerTime = averageAnswerTime;
        this.canRestartTraining = canRestartTraining;
    }
}
