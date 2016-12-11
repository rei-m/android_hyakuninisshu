package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

import java.util.List;

public class MaterialViewModel {

    public final List<KarutaViewModel> karutaList;

    public static class KarutaViewModel {

        public final int karutaNo;

        public final String karutaImageNo;

        public final String creator;

        public final String firstPhrase;

        public final String secondPhrase;

        public final String thirdPhrase;

        public final String fourthPhrase;

        public final String fifthPhrase;

        public KarutaViewModel(int karutaNo,
                               @NonNull String karutaImageNo,
                               @NonNull String creator,
                               @NonNull String firstPhrase,
                               @NonNull String secondPhrase,
                               @NonNull String thirdPhrase,
                               @NonNull String fourthPhrase,
                               @NonNull String fifthPhrase) {
            this.karutaNo = karutaNo;
            this.karutaImageNo = karutaImageNo;
            this.creator = creator;
            this.firstPhrase = firstPhrase;
            this.secondPhrase = secondPhrase;
            this.thirdPhrase = thirdPhrase;
            this.fourthPhrase = fourthPhrase;
            this.fifthPhrase = fifthPhrase;
        }
    }

    public MaterialViewModel(@NonNull List<KarutaViewModel> karutaList) {
        this.karutaList = karutaList;
    }
}
