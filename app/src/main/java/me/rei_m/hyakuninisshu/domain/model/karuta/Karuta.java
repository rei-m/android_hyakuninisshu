package me.rei_m.hyakuninisshu.domain.model.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;

public class Karuta extends AbstractEntity<Karuta, KarutaIdentifier> {

    private final String creator;

    private TopPhrase topPhrase;

    private BottomPhrase bottomPhrase;

    private final Kimariji kimariji;

    private final ImageNo imageNo;

    private final String translation;

    private final Color color;

    public Karuta(@NonNull KarutaIdentifier identifier,
                  @NonNull String creator,
                  @NonNull TopPhrase topPhrase,
                  @NonNull BottomPhrase bottomPhrase,
                  @NonNull Kimariji kimariji,
                  @NonNull ImageNo imageNo,
                  @NonNull String translation,
                  @NonNull Color color) {
        super(identifier);
        this.creator = creator;
        this.topPhrase = topPhrase;
        this.bottomPhrase = bottomPhrase;
        this.kimariji = kimariji;
        this.imageNo = imageNo;
        this.translation = translation;
        this.color = color;
    }

    public String creator() {
        return creator;
    }

    public TopPhrase topPhrase() {
        return topPhrase;
    }

    public BottomPhrase bottomPhrase() {
        return bottomPhrase;
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

    public boolean isCollect(BottomPhrase bottomPhrase) {
        return this.bottomPhrase.equals(bottomPhrase);
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

        TopPhrase topPhrase = new TopPhrase(
                this.identifier(),
                new Phrase(firstPhraseKana, firstPhraseKanji),
                new Phrase(secondPhraseKana, secondPhraseKanji),
                new Phrase(thirdPhraseKana, thirdPhraseKanji)
        );

        BottomPhrase bottomPhrase = new BottomPhrase(
                this.identifier(),
                new Phrase(fourthPhraseKana, fourthPhraseKanji),
                new Phrase(fifthPhraseKana, fifthPhraseKanji)
        );

        this.topPhrase = topPhrase;
        this.bottomPhrase = bottomPhrase;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Karuta karuta = (Karuta) o;

        if (!creator.equals(karuta.creator)) return false;
        if (!topPhrase.equals(karuta.topPhrase)) return false;
        if (!bottomPhrase.equals(karuta.bottomPhrase)) return false;
        if (!kimariji.equals(karuta.kimariji)) return false;
        if (!imageNo.equals(karuta.imageNo)) return false;
        if (!translation.equals(karuta.translation)) return false;
        return color.equals(karuta.color);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + topPhrase.hashCode();
        result = 31 * result + bottomPhrase.hashCode();
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
                ", topPhrase=" + topPhrase +
                ", bottomPhrase=" + bottomPhrase +
                ", kimariji=" + kimariji +
                ", imageNo=" + imageNo +
                ", translation='" + translation + '\'' +
                ", color=" + color +
                "} " + super.toString();
    }
}
