package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class ShimoNoKu extends AbstractEntity<ShimoNoKu, ShimoNoKuIdentifier> {

    private final Phrase fourth;

    private final Phrase fifth;

    public ShimoNoKu(@NonNull ShimoNoKuIdentifier identifier,
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

        ShimoNoKu shimoNoKu = (ShimoNoKu) o;

        if (!fourth.equals(shimoNoKu.fourth)) return false;
        return fifth.equals(shimoNoKu.fifth);
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
        return "ShimoNoKu{" +
                "fourth=" + fourth +
                ", fifth=" + fifth +
                "} " + super.toString();
    }
}
