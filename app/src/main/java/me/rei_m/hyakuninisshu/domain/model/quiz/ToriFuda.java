package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class ToriFuda implements ValueObject {

    private final String fourthPhrase;

    private final String fifthPhrase;

    public ToriFuda(@NonNull String fourthPhrase, @NonNull String fifthPhrase) {
        this.fourthPhrase = fourthPhrase;
        this.fifthPhrase = fifthPhrase;
    }

    public String getFourthPhrase() {
        return fourthPhrase;
    }

    public String getFifthPhrase() {
        return fifthPhrase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToriFuda toriFuda = (ToriFuda) o;

        return fourthPhrase.equals(toriFuda.fourthPhrase) && fifthPhrase.equals(toriFuda.fifthPhrase);
    }

    @Override
    public int hashCode() {
        int result = fourthPhrase.hashCode();
        result = 31 * result + fifthPhrase.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ToriFuda{" +
                "fourthPhrase='" + fourthPhrase + '\'' +
                ", fifthPhrase='" + fifthPhrase + '\'' +
                '}';
    }
}
