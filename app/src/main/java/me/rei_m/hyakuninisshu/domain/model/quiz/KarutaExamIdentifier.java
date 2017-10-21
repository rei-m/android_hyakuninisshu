package me.rei_m.hyakuninisshu.domain.model.quiz;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaExamIdentifier implements EntityIdentifier<KarutaExam> {

    private static final String kind = KarutaExam.class.getSimpleName();

    private final long value;

    public KarutaExamIdentifier(long value) {
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

        KarutaExamIdentifier that = (KarutaExamIdentifier) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toString() {
        return "KarutaExamIdentifier{" +
                "value=" + value +
                '}';
    }
}
