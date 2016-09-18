package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class FirstHalf implements ValueObject {

    private final KarutaPart first;

    private final KarutaPart second;
    
    private final KarutaPart third;

    public FirstHalf(@NonNull KarutaPart first, @NonNull KarutaPart second, @NonNull KarutaPart third) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FirstHalf firstHalf = (FirstHalf) o;

        return first.equals(firstHalf.first) && second.equals(firstHalf.second) && third.equals(firstHalf.third);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
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
                '}';
    }
}
