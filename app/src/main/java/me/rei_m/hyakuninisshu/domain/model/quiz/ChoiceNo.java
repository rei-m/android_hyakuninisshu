package me.rei_m.hyakuninisshu.domain.model.quiz;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public enum ChoiceNo implements ValueObject {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    public static ChoiceNo forValue(int value) {
        for (ChoiceNo choiceNo : values()) {
            if (choiceNo.value() == value) {
                return choiceNo;
            }
        }
        throw new AssertionError("no enum found. value is " + value);
    }

    private final int value;

    ChoiceNo(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public int asIndex() {
        return value - 1;
    }

    @Override
    public String toString() {
        return "ChoiceNo{" +
                "value=" + value +
                "} " + super.toString();
    }
}
