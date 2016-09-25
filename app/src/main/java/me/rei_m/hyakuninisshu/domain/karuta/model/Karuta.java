package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class Karuta extends AbstractEntity<Karuta, KarutaIdentifier> {

    private final String creator;

    private final FirstHalf firstHalf;

    private final SecondHalf secondHalf;

    private final int kimariji;

    private final String imageNo;

    Karuta(@NonNull KarutaIdentifier identifier,
           @NonNull String creator,
           @NonNull FirstHalf firstHalf,
           @NonNull SecondHalf secondHalf,
           int kimariji,
           @NonNull String imageNo) {
        super(identifier);
        this.creator = creator;
        this.firstHalf = firstHalf;
        this.secondHalf = secondHalf;
        this.kimariji = kimariji;
        this.imageNo = imageNo;
    }

    public FirstHalf getFirstHalf() {
        return firstHalf;
    }

    public SecondHalf getSecondHalf() {
        return secondHalf;
    }

    public int getKimariji() {
        return kimariji;
    }

    public String getImageNo() {
        return imageNo;
    }

    public boolean isCollectSecondHalf(SecondHalf secondHalf) {
        return this.secondHalf.equals(secondHalf);
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
