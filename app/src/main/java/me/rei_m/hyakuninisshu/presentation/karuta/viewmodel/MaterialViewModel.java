package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

import java.util.List;

public class MaterialViewModel {

    public final List<KarutaViewModel> karutaList;

    public static class KarutaViewModel {

        public final int karutaNo;

        public final String karutaNoText;

        public final String karutaImageNo;

        public final String creator;

        public final String topPhrase;

        public final String bottomPhrase;

        public KarutaViewModel(int karutaNo,
                               @NonNull String karutaNoText,
                               @NonNull String karutaImageNo,
                               @NonNull String creator,
                               @NonNull String topPhrase,
                               @NonNull String bottomPhrase) {
            this.karutaNo = karutaNo;
            this.karutaNoText = karutaNoText;
            this.karutaImageNo = karutaImageNo;
            this.creator = creator;
            this.topPhrase = topPhrase;
            this.bottomPhrase = bottomPhrase;
        }
    }

    public MaterialViewModel(@NonNull List<KarutaViewModel> karutaList) {
        this.karutaList = karutaList;
    }
}
