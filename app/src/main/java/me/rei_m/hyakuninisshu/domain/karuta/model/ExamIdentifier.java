package me.rei_m.hyakuninisshu.domain.karuta.model;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class ExamIdentifier implements EntityIdentifier<KarutaExam> {

    private final String kind = "KarutaExam";

    private final long value;

    public ExamIdentifier(long value) {
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

        ExamIdentifier that = (ExamIdentifier) o;

        return value == that.value && kind.equals(that.kind);

    }

    @Override
    public int hashCode() {
        int result = kind.hashCode();
        result = 31 * result + (int) (value ^ (value >>> 32));
        return result;
    }


}
