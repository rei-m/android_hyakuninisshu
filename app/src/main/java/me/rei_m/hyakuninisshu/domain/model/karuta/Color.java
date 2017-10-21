package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public enum Color implements ValueObject {
    BLUE("blue"),
    PINK("pink"),
    YELLOW("yellow"),
    GREEN("green"),
    ORANGE("orange");

    public static Color forValue(String value) {
        for (Color color : values()) {
            if (color.value().equals(value)) {
                return color;
            }
        }
        throw new AssertionError("no enum found. value is " + value);
    }

    private final String value;

    Color(@NonNull String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "Color{" +
                "value='" + value + '\'' +
                "} " + super.toString();
    }
}
