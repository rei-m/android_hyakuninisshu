package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class SecondHalf implements ValueObject {

    private final KarutaPart forth;

    private final KarutaPart fifth;

    public SecondHalf(@NonNull KarutaPart forth, @NonNull KarutaPart fifth) {
        this.forth = forth;
        this.fifth = fifth;
    }

    public KarutaPart getForth() {
        return forth;
    }

    public KarutaPart getFifth() {
        return fifth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecondHalf that = (SecondHalf) o;

        return forth.equals(that.forth) && fifth.equals(that.fifth);
    }

    @Override
    public int hashCode() {
        int result = forth.hashCode();
        result = 31 * result + fifth.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SecondHalf{" +
                "forth=" + forth +
                ", fifth=" + fifth +
                '}';
    }
}
