package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class MaterialDetailViewModel {

    public final String karutaImageNo;

    public final String creator;

    public final int kimariji;

    public final String firstPhraseKana;

    public final String firstPhraseKanji;

    public final String secondPhraseKana;

    public final String secondPhraseKanji;

    public final String thirdPhraseKana;

    public final String thirdPhraseKanji;

    public final String fourthPhraseKana;

    public final String fourthPhraseKanji;

    public final String fifthPhraseKana;

    public final String fifthPhraseKanji;

    public MaterialDetailViewModel(@NonNull String karutaImageNo,
                                   @NonNull String creator,
                                   int kimariji,
                                   @NonNull String firstPhraseKana,
                                   @NonNull String firstPhraseKanji,
                                   @NonNull String secondPhraseKana,
                                   @NonNull String secondPhraseKanji,
                                   @NonNull String thirdPhraseKana,
                                   @NonNull String thirdPhraseKanji,
                                   @NonNull String fourthPhraseKana,
                                   @NonNull String fourthPhraseKanji,
                                   @NonNull String fifthPhraseKana,
                                   @NonNull String fifthPhraseKanji) {
        this.karutaImageNo = karutaImageNo;
        this.creator = creator;
        this.kimariji = kimariji;
        this.firstPhraseKana = firstPhraseKana;
        this.firstPhraseKanji = firstPhraseKanji;
        this.secondPhraseKana = secondPhraseKana;
        this.secondPhraseKanji = secondPhraseKanji;
        this.thirdPhraseKana = thirdPhraseKana;
        this.thirdPhraseKanji = thirdPhraseKanji;
        this.fourthPhraseKana = fourthPhraseKana;
        this.fourthPhraseKanji = fourthPhraseKanji;
        this.fifthPhraseKana = fifthPhraseKana;
        this.fifthPhraseKanji = fifthPhraseKanji;
    }
}
