package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizResult implements ValueObject {

    public final KarutaIdentifier collectKarutaId;

    public final boolean isCollect;

    public final long answerTime;

    public KarutaQuizResult(@NonNull KarutaIdentifier collectKarutaId,
                            boolean isCollect,
                            long answerTime) {
        this.collectKarutaId = collectKarutaId;
        this.isCollect = isCollect;
        this.answerTime = answerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizResult result = (KarutaQuizResult) o;

        return isCollect == result.isCollect &&
                answerTime == result.answerTime &&
                collectKarutaId.equals(result.collectKarutaId);

    }

    @Override
    public int hashCode() {
        int result = collectKarutaId.hashCode();
        result = 31 * result + (isCollect ? 1 : 0);
        result = 31 * result + (int) (answerTime ^ (answerTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizResult{" +
                "collectKarutaId=" + collectKarutaId +
                ", isCollect=" + isCollect +
                ", answerTime=" + answerTime +
                '}';
    }
}
