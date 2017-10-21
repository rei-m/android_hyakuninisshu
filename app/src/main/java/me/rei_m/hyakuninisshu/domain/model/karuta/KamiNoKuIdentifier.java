package me.rei_m.hyakuninisshu.domain.model.karuta;

import java.util.UUID;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KamiNoKuIdentifier implements EntityIdentifier<KamiNoKu> {

    private static final String kind = KamiNoKu.class.getSimpleName();

    private final String value;

    public KamiNoKuIdentifier() {
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

        KamiNoKuIdentifier that = (KamiNoKuIdentifier) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "KamiNoKuIdentifier{" +
                "value='" + value + '\'' +
                '}';
    }
}
