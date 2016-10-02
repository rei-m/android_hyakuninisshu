package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

class TopPhrase extends AbstractEntity<TopPhrase, KarutaIdentifier> {

    private final Phrase first;

    private final Phrase second;

    private final Phrase third;

    TopPhrase(@NonNull KarutaIdentifier identifier,
              @NonNull Phrase first,
              @NonNull Phrase second,
              @NonNull Phrase third) {
        super(identifier);
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Phrase getFirst() {
        return first;
    }

    public Phrase getSecond() {
        return second;
    }

    public Phrase getThird() {
        return third;
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
