package me.rei_m.hyakuninisshu.domain.model.quiz;

import java.util.UUID;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaQuizIdentifier implements EntityIdentifier<KarutaQuiz> {

    private final String kind = "KarutaQuiz";

    private final String value;

    public KarutaQuizIdentifier() {
        this.value = UUID.randomUUID().toString();
    }

    public KarutaQuizIdentifier(String value) {
        this.value = value;
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

        KarutaQuizIdentifier that = (KarutaQuizIdentifier) o;

        return kind.equals(that.kind) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = kind.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizIdentifier{" +
                "kind='" + kind + '\'' +
                ", value=" + value +
                '}';
    }
}
