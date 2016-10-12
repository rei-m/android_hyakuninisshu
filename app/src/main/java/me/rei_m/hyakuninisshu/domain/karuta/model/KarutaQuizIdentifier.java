package me.rei_m.hyakuninisshu.domain.karuta.model;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaQuizIdentifier implements EntityIdentifier<KarutaQuiz> {

    private final String kind = "KarutaQuiz";

    private final long value;

    KarutaQuizIdentifier(long value) {
        this.value = value;
    }

    public long getValue() {
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

        KarutaQuizIdentifier that = (KarutaQuizIdentifier) o;

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
