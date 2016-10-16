package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizResult implements ValueObject {

    private final KarutaQuizIdentifier identifier;

    private final boolean isCollect;

    private final long answerTime;

    public KarutaQuizResult(@NonNull KarutaQuizIdentifier identifier,
                            boolean isCollect,
                            long answerTime) {
        this.identifier = identifier;
        this.isCollect = isCollect;
        this.answerTime = answerTime;
    }

    public KarutaQuizIdentifier getIdentifier() {
        return identifier;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public long getAnswerTime() {
        return answerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizResult that = (KarutaQuizResult) o;

        return isCollect == that.isCollect && answerTime == that.answerTime && identifier.equals(that.identifier);

    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + (isCollect ? 1 : 0);
        result = 31 * result + (int) (answerTime ^ (answerTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizResult{" +
                "identifier=" + identifier +
                ", isCollect=" + isCollect +
                ", answerTime=" + answerTime +
                '}';
    }
}
