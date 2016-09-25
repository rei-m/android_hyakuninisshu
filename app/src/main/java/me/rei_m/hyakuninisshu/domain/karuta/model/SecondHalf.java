package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

class SecondHalf extends AbstractEntity<SecondHalf, KarutaIdentifier> {

    private final KarutaPart fourth;

    private final KarutaPart fifth;


    SecondHalf(@NonNull KarutaIdentifier identifier,
               @NonNull KarutaPart fourth,
               @NonNull KarutaPart fifth) {
        super(identifier);
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public KarutaPart getFourth() {
        return fourth;
    }

    public KarutaPart getFifth() {
        return fifth;
    }

    @Override
    public String toString() {
        return "SecondHalf{" +
                "fourth=" + fourth +
                ", fifth=" + fifth +
                "} " + super.toString();
    }
}
