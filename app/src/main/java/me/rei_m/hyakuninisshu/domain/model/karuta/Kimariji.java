package me.rei_m.hyakuninisshu.domain.model.karuta;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class Kimariji implements ValueObject {

    public static final int MAX = 6;

    public static final int MIN = 1;

    private int value;

    public Kimariji(int value) {
        if (MIN <= value && value <= MAX) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Kimariji is Invalid, value is " + value);
        }
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kimariji kimariji = (Kimariji) o;

        return value == kimariji.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "Kimariji{" +
                "value=" + value +
                '}';
    }
}
