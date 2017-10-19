package me.rei_m.hyakuninisshu.domain.model.karuta;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaIdentifier implements EntityIdentifier<Karuta> {

    private final String kind = "Karuta";

    private final long value;

    public KarutaIdentifier(long value) {
        this.value = value;
    }

    public long value() {
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

        KarutaIdentifier that = (KarutaIdentifier) o;

        return value == that.value && kind.equals(that.kind);
    }

    @Override
    public int hashCode() {
        int result = kind.hashCode();
        result = 31 * result + (int) (value ^ (value >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "KarutaIdentifier{" +
                "kind='" + kind + '\'' +
                ", value=" + value +
                '}';
    }
}
