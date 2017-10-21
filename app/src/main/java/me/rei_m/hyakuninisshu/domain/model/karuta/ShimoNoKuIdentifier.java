package me.rei_m.hyakuninisshu.domain.model.karuta;

import java.util.UUID;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class ShimoNoKuIdentifier implements EntityIdentifier<ShimoNoKu> {

    private static final String kind = ShimoNoKu.class.getSimpleName();

    private final String value;

    public ShimoNoKuIdentifier() {
        this.value = UUID.randomUUID().toString();
    }

    public String value() {
        return value;
    }

    @Override
    public String kind() {
        return kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShimoNoKuIdentifier that = (ShimoNoKuIdentifier) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "ShimoNoKuIdentifier{" +
                "value='" + value + '\'' +
                '}';
    }
}
