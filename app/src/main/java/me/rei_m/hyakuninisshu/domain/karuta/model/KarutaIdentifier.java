package me.rei_m.hyakuninisshu.domain.karuta.model;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

class KarutaIdentifier implements EntityIdentifier<Karuta> {

    private String kind = "Karuta";

    private int value;

    KarutaIdentifier(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getKind() {
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
        result = 31 * result + value;
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
