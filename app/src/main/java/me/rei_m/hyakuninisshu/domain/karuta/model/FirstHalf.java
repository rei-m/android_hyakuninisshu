package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

class FirstHalf extends AbstractEntity<FirstHalf, KarutaIdentifier> {

    private final KarutaPart first;

    private final KarutaPart second;

    private final KarutaPart third;

    FirstHalf(@NonNull KarutaIdentifier identifier,
              @NonNull KarutaPart first,
              @NonNull KarutaPart second,
              @NonNull KarutaPart third) {
        super(identifier);
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public KarutaPart getFirst() {
        return first;
    }

    public KarutaPart getSecond() {
        return second;
    }

    public KarutaPart getThird() {
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
