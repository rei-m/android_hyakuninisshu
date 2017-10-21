package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.ValueObject;

public class YomiFuda implements ValueObject {

    private final String firstPhrase;

    private final String secondPhrase;

    private final String thirdPhrase;

    public YomiFuda(@NonNull String firstPhrase, @NonNull String secondPhrase, @NonNull String thirdPhrase) {
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
        this.thirdPhrase = thirdPhrase;
    }

    public String firstPhrase() {
        return firstPhrase;
    }

    public String secondPhrase() {
        return secondPhrase;
    }

    public String thirdPhrase() {
        return thirdPhrase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YomiFuda yomiFuda = (YomiFuda) o;

        return firstPhrase.equals(yomiFuda.firstPhrase) &&
                secondPhrase.equals(yomiFuda.secondPhrase) &&
                thirdPhrase.equals(yomiFuda.thirdPhrase);
    }

    @Override
    public int hashCode() {
        int result = firstPhrase.hashCode();
        result = 31 * result + secondPhrase.hashCode();
        result = 31 * result + thirdPhrase.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "YomiFuda{" +
                "firstPhrase='" + firstPhrase + '\'' +
                ", secondPhrase='" + secondPhrase + '\'' +
                ", thirdPhrase='" + thirdPhrase + '\'' +
                '}';
    }
}
