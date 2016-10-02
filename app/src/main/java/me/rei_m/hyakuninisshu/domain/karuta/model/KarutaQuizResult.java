package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizResult implements ValueObject {

    private final Karuta karuta;

    private final boolean isCollect;

    private final long answerTime;

    public KarutaQuizResult(@NonNull Karuta karuta,
                            boolean isCollect,
                            long answerTime) {
        this.karuta = karuta;
        this.isCollect = isCollect;
        this.answerTime = answerTime;
    }

    public Karuta getKaruta() {
        return karuta;
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

        return isCollect == that.isCollect && answerTime == that.answerTime && karuta.equals(that.karuta);
    }

    @Override
    public int hashCode() {
        int result = karuta.hashCode();
        result = 31 * result + (isCollect ? 1 : 0);
        result = 31 * result + (int) (answerTime ^ (answerTime >>> 32));
        return result;
    }
}
