package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class MaterialDetailViewModel {

    public final String karutaNo;

    public final String karutaImageNo;

    public final String creator;

    public final int kimariji;

    public final String topPhraseKanji;

    public final String bottomPhraseKanji;

    public final String topPhraseKana;

    public final String bottomPhraseKana;

    public final String translation;

    public MaterialDetailViewModel(@NonNull String karutaNo,
                                   @NonNull String karutaImageNo,
                                   @NonNull String creator,
                                   int kimariji,
                                   @NonNull String topPhraseKanji,
                                   @NonNull String bottomPhraseKanji,
                                   @NonNull String topPhraseKana,
                                   @NonNull String bottomPhraseKana,
                                   @NonNull String translation) {
        this.karutaNo = karutaNo;
        this.karutaImageNo = karutaImageNo;
        this.creator = creator;
        this.kimariji = kimariji;
        this.topPhraseKanji = topPhraseKanji;
        this.bottomPhraseKanji = bottomPhraseKanji;
        this.topPhraseKana = topPhraseKana;
        this.bottomPhraseKana = bottomPhraseKana;
        this.translation = translation;
    }
}
