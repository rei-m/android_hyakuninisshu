package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class TopPhrase extends AbstractEntity<TopPhrase, KarutaIdentifier> {

    private final Phrase first;

    private final Phrase second;

    private final Phrase third;

    public TopPhrase(@NonNull KarutaIdentifier identifier,
                     @NonNull Phrase first,
                     @NonNull Phrase second,
                     @NonNull Phrase third) {
        super(identifier);
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Phrase first() {
        return first;
    }

    public Phrase second() {
        return second;
    }

    public Phrase third() {
        return third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TopPhrase topPhrase = (TopPhrase) o;

        return first.equals(topPhrase.first) && second.equals(topPhrase.second) && third.equals(topPhrase.third);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + first.hashCode();
        result = 31 * result + second.hashCode();
        result = 31 * result + third.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FirstHalf{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                "} " + super.toString();
    }
}
