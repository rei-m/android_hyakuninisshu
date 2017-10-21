package me.rei_m.hyakuninisshu.domain.model.karuta;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public enum Kimariji implements ValueObject {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    public static Kimariji forValue(int value) {
        for(Kimariji kimariji : values()) {
            if (kimariji.value() == value) {
                return kimariji;
            }
        }
        throw new AssertionError("no enum found. value is " + value);
    }

    private int value;

    Kimariji(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return "Kimariji{" +
                "value=" + value +
                '}';
    }
}
