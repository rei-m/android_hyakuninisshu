package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class Karuta extends AbstractEntity<Karuta, KarutaIdentifier> {

    public static final int NUMBER_OF_KARUTA = 100;

    private final String creator;

    private KamiNoKu kamiNoKu;

    private ShimoNoKu shimoNoKu;

    private final Kimariji kimariji;

    private final ImageNo imageNo;

    private final String translation;

    private final Color color;

    public Karuta(@NonNull KarutaIdentifier identifier,
                  @NonNull String creator,
                  @NonNull KamiNoKu kamiNoKu,
                  @NonNull ShimoNoKu shimoNoKu,
                  @NonNull Kimariji kimariji,
                  @NonNull ImageNo imageNo,
                  @NonNull String translation,
                  @NonNull Color color) {
        super(identifier);
        this.creator = creator;
        this.kamiNoKu = kamiNoKu;
        this.shimoNoKu = shimoNoKu;
        this.kimariji = kimariji;
        this.imageNo = imageNo;
        this.translation = translation;
        this.color = color;
    }

    public String creator() {
        return creator;
    }

    public KamiNoKu kamiNoKu() {
        return kamiNoKu;
    }

    public ShimoNoKu shimoNoKu() {
        return shimoNoKu;
    }

    public Kimariji kimariji() {
        return kimariji;
    }

    public ImageNo imageNo() {
        return imageNo;
    }

    public String translation() {
        return translation;
    }

    public Color color() {
        return color;
    }

    public Karuta updatePhrase(@NonNull String firstPhraseKanji,
                               @NonNull String firstPhraseKana,
                               @NonNull String secondPhraseKanji,
                               @NonNull String secondPhraseKana,
                               @NonNull String thirdPhraseKanji,
                               @NonNull String thirdPhraseKana,
                               @NonNull String fourthPhraseKanji,
                               @NonNull String fourthPhraseKana,
                               @NonNull String fifthPhraseKanji,
                               @NonNull String fifthPhraseKana) {

        KamiNoKu kamiNoKu = new KamiNoKu(
                this.kamiNoKu.identifier(),
                new Phrase(firstPhraseKana, firstPhraseKanji),
                new Phrase(secondPhraseKana, secondPhraseKanji),
                new Phrase(thirdPhraseKana, thirdPhraseKanji)
        );

        ShimoNoKu shimoNoKu = new ShimoNoKu(
                this.shimoNoKu.identifier(),
                new Phrase(fourthPhraseKana, fourthPhraseKanji),
                new Phrase(fifthPhraseKana, fifthPhraseKanji)
        );

        this.kamiNoKu = kamiNoKu;
        this.shimoNoKu = shimoNoKu;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Karuta karuta = (Karuta) o;

        if (!creator.equals(karuta.creator)) return false;
        if (!kamiNoKu.equals(karuta.kamiNoKu)) return false;
        if (!shimoNoKu.equals(karuta.shimoNoKu)) return false;
        if (!kimariji.equals(karuta.kimariji)) return false;
        if (!imageNo.equals(karuta.imageNo)) return false;
        if (!translation.equals(karuta.translation)) return false;

        return color.equals(karuta.color);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + kamiNoKu.hashCode();
        result = 31 * result + shimoNoKu.hashCode();
        result = 31 * result + kimariji.hashCode();
        result = 31 * result + imageNo.hashCode();
        result = 31 * result + translation.hashCode();
        result = 31 * result + color.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "Karuta{" +
                "creator='" + creator + '\'' +
                ", kamiNoKu=" + kamiNoKu +
                ", shimoNoKu=" + shimoNoKu +
                ", kimariji=" + kimariji +
                ", imageNo=" + imageNo +
                ", translation='" + translation + '\'' +
                ", color=" + color +
                "} " + super.toString();
    }
}
