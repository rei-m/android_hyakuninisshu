package me.rei_m.hyakuninisshu.domain.model.quiz;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizPosition implements ValueObject {

    private final int totalCount;

    private final int answeredCount;

    public KarutaQuizPosition(int totalCount, int answeredCount) {
        this.totalCount = totalCount;
        this.answeredCount = answeredCount;
    }

    public String value() {
        return String.valueOf(answeredCount + 1) + " / " + String.valueOf(totalCount);
    }
}
