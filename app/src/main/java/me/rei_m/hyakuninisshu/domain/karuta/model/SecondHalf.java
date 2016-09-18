package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class SecondHalf implements ValueObject {

    private final KarutaPart fourth;

    private final KarutaPart fifth;

    public SecondHalf(@NonNull KarutaPart fourth, @NonNull KarutaPart fifth) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecondHalf that = (SecondHalf) o;

        return fourth.equals(that.fourth) && fifth.equals(that.fifth);
    }

    @Override
    public int hashCode() {
        int result = fourth.hashCode();
        result = 31 * result + fifth.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SecondHalf{" +
                "fourth=" + fourth +
                ", fifth=" + fifth +
                '}';
    }
}
