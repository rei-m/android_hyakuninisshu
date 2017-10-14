package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

public class KarutaQuizResult implements ValueObject {

    public final KarutaIdentifier collectKarutaId;

    public final int choiceNo;

    public final boolean isCollect;

    public final long answerTime;

    KarutaQuizResult(@NonNull KarutaIdentifier collectKarutaId,
                     int choiceNo,
                     boolean isCollect,
                     long answerTime) {
        this.collectKarutaId = collectKarutaId;
        this.choiceNo = choiceNo;
        this.isCollect = isCollect;
        this.answerTime = answerTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizResult that = (KarutaQuizResult) o;

        return choiceNo == that.choiceNo &&
                isCollect == that.isCollect &&
                answerTime == that.answerTime &&
                collectKarutaId.equals(that.collectKarutaId);
    }

    @Override
    public int hashCode() {
        int result = collectKarutaId.hashCode();
        result = 31 * result + choiceNo;
        result = 31 * result + (isCollect ? 1 : 0);
        result = 31 * result + (int) (answerTime ^ (answerTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizResult{" +
                "collectKarutaId=" + collectKarutaId +
                ", choiceNo=" + choiceNo +
                ", isCollect=" + isCollect +
                ", answerTime=" + answerTime +
                '}';
    }
}
