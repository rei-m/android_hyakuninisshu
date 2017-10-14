package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class BottomPhrase extends AbstractEntity<BottomPhrase, KarutaIdentifier> {

    private final Phrase fourth;

    private final Phrase fifth;

    public BottomPhrase(@NonNull KarutaIdentifier identifier,
                        @NonNull Phrase fourth,
                        @NonNull Phrase fifth) {
        super(identifier);
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public Phrase fourth() {
        return fourth;
    }

    public Phrase fifth() {
        return fifth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BottomPhrase that = (BottomPhrase) o;

        return fourth.equals(that.fourth) && fifth.equals(that.fifth);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + fourth.hashCode();
        result = 31 * result + fifth.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SecondHalf{" +
                "fourth=" + fourth +
                ", fifth=" + fifth +
                "} " + super.toString();
    }
}
