package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class Karuta extends AbstractEntity<Karuta, KarutaIdentifier> {

    private final FirstHalf firstHalf;

    private final SecondHalf secondHalf;

    private final int kimariji;

    private final String imageNo;

    public Karuta(@NonNull KarutaIdentifier identifier,
                  @NonNull FirstHalf firstHalf,
                  @NonNull SecondHalf secondHalf,
                  int kimariji,
                  @NonNull String imageNo) {
        super(identifier);
        this.firstHalf = firstHalf;
        this.secondHalf = secondHalf;
        this.kimariji = kimariji;
        this.imageNo = imageNo;
    }

    @Override
    public String toString() {
        return "Karuta{" +
                "firstHalf=" + firstHalf +
                ", secondHalf=" + secondHalf +
                ", kimariji=" + kimariji +
                ", imageNo='" + imageNo + '\'' +
                "} " + super.toString();
    }
}
