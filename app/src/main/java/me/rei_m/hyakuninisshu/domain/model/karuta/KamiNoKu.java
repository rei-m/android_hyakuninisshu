package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class KamiNoKu extends AbstractEntity<KamiNoKu, KamiNoKuIdentifier> {

    private final Phrase first;

    private final Phrase second;

    private final Phrase third;

    public KamiNoKu(@NonNull KamiNoKuIdentifier identifier,
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

        KamiNoKu kamiNoKu = (KamiNoKu) o;

        if (!first.equals(kamiNoKu.first)) return false;
        if (!second.equals(kamiNoKu.second)) return false;

        return third.equals(kamiNoKu.third);
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
        return "KamiNoKu{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                "} " + super.toString();
    }
}
