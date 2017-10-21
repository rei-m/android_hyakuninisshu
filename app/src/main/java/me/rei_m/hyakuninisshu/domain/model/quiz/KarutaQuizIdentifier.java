package me.rei_m.hyakuninisshu.domain.model.quiz;

import java.util.UUID;

import me.rei_m.hyakuninisshu.domain.EntityIdentifier;

public class KarutaQuizIdentifier implements EntityIdentifier<KarutaQuiz> {

    private static final String kind = KarutaQuiz.class.getSimpleName();

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

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "KarutaQuizIdentifier{" +
                "value='" + value + '\'' +
                '}';
    }
}
