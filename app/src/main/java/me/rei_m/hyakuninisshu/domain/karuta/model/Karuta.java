package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class Karuta extends AbstractEntity<Karuta, KarutaIdentifier> {

    private final String creator;

    private final TopPhrase topPhrase;

    private final BottomPhrase bottomPhrase;

    private final int kimariji;

    private final String imageNo;

    private final String translation;

    Karuta(@NonNull KarutaIdentifier identifier,
           @NonNull String creator,
           @NonNull TopPhrase topPhrase,
           @NonNull BottomPhrase bottomPhrase,
           int kimariji,
           @NonNull String imageNo,
           @NonNull String translation) {
        super(identifier);
        this.creator = creator;
        this.topPhrase = topPhrase;
        this.bottomPhrase = bottomPhrase;
        this.kimariji = kimariji;
        this.imageNo = imageNo;
        this.translation = translation;
    }

    public String getCreator() {
        return creator;
    }

    public TopPhrase getTopPhrase() {
        return topPhrase;
    }

    public BottomPhrase getBottomPhrase() {
        return bottomPhrase;
    }

    public int getKimariji() {
        return kimariji;
    }

    public String getImageNo() {
        return imageNo;
    }

    public String getTranslation() {
        return translation;
    }

    public boolean isCollect(BottomPhrase bottomPhrase) {
        return this.bottomPhrase.equals(bottomPhrase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Karuta karuta = (Karuta) o;

        return kimariji == karuta.kimariji &&
                creator.equals(karuta.creator) &&
                topPhrase.equals(karuta.topPhrase) &&
                bottomPhrase.equals(karuta.bottomPhrase) &&
                imageNo.equals(karuta.imageNo) &&
                translation.equals(karuta.translation);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + topPhrase.hashCode();
        result = 31 * result + bottomPhrase.hashCode();
        result = 31 * result + kimariji;
        result = 31 * result + imageNo.hashCode();
        result = 31 * result + translation.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Karuta{" +
                "creator='" + creator + '\'' +
                ", topPhrase=" + topPhrase +
                ", bottomPhrase=" + bottomPhrase +
                ", kimariji=" + kimariji +
                ", imageNo='" + imageNo + '\'' +
                ", translation='" + translation + '\'' +
                "} " + super.toString();
    }
}
