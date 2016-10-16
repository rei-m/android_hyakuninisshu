package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import java.util.Date;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class KarutaQuizAnswer implements ValueObject {

    private final KarutaQuizIdentifier identifier;

    private final Karuta collectKaruta;

    private final Date startDate;

    public KarutaQuizAnswer(@NonNull KarutaQuizIdentifier identifier,
                            @NonNull Karuta collectKaruta,
                            @NonNull Date startDate) {
        this.identifier = identifier;
        this.collectKaruta = collectKaruta;
        this.startDate = startDate;
    }

    public KarutaQuizResult verify(@NonNull BottomPhrase bottomPhrase,
                                   @NonNull Date answerDate) {
        return new KarutaQuizResult(identifier,
                collectKaruta.isCollect(bottomPhrase),
                answerDate.getTime() - startDate.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizAnswer that = (KarutaQuizAnswer) o;

        return identifier.equals(that.identifier) && collectKaruta.equals(that.collectKaruta) && startDate.equals(that.startDate);

    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + collectKaruta.hashCode();
        result = 31 * result + startDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizAnswer{" +
                "identifier=" + identifier +
                ", collectKaruta=" + collectKaruta +
                ", startDate=" + startDate +
                '}';
    }
}
