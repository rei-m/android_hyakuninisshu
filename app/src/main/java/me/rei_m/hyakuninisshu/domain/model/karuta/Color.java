package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class Color implements ValueObject {

    private final String value;

    public Color(@NonNull String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        return value.equals(color.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Color{" +
                "value='" + value + '\'' +
                '}';
    }
}
