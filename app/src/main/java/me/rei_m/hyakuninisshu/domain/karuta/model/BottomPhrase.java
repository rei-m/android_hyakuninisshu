package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class BottomPhrase extends AbstractEntity<BottomPhrase, KarutaIdentifier> {

    private final Phrase fourth;

    private final Phrase fifth;
    
    BottomPhrase(@NonNull KarutaIdentifier identifier,
                 @NonNull Phrase fourth,
                 @NonNull Phrase fifth) {
        super(identifier);
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public Phrase getFourth() {
        return fourth;
    }

    public Phrase getFifth() {
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
