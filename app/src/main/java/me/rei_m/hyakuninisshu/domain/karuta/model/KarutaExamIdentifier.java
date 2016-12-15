package me.rei_m.hyakuninisshu.domain.karuta.model;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaExamIdentifier implements EntityIdentifier<KarutaExam> {

    private final String kind = "KarutaExam";

    private final long value;

    public KarutaExamIdentifier(long value) {
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

        KarutaExamIdentifier that = (KarutaExamIdentifier) o;

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
        return "KarutaExamIdentifier{" +
                "kind='" + kind + '\'' +
                ", value=" + value +
                '}';
    }
}
