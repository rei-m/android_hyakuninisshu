package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.dialog;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class ConfirmMaterialEditDialogFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<String> firstPhraseKanji = new ObservableField<>();

    public final ObservableField<String> firstPhraseKana = new ObservableField<>();

    public final ObservableField<String> secondPhraseKanji = new ObservableField<>();

    public final ObservableField<String> secondPhraseKana = new ObservableField<>();

    public final ObservableField<String> thirdPhraseKanji = new ObservableField<>();

    public final ObservableField<String> thirdPhraseKana = new ObservableField<>();

    public final ObservableField<String> fourthPhraseKanji = new ObservableField<>();

    public final ObservableField<String> fourthPhraseKana = new ObservableField<>();

    public final ObservableField<String> fifthPhraseKanji = new ObservableField<>();

    public final ObservableField<String> fifthPhraseKana = new ObservableField<>();

    public void onCreate(@NonNull String firstPhraseKanji,
                         @NonNull String firstPhraseKana,
                         @NonNull String secondPhraseKanji,
                         @NonNull String secondPhraseKana,
                         @NonNull String thirdPhraseKanji,
                         @NonNull String thirdPhraseKana,
                         @NonNull String fourthPhraseKanji,
                         @NonNull String fourthPhraseKana,
                         @NonNull String fifthPhraseKanji,
                         @NonNull String fifthPhraseKana) {
        this.firstPhraseKanji.set(firstPhraseKanji);
        this.firstPhraseKana.set(firstPhraseKana);
        this.secondPhraseKanji.set(secondPhraseKanji);
        this.secondPhraseKana.set(secondPhraseKana);
        this.thirdPhraseKanji.set(thirdPhraseKanji);
        this.thirdPhraseKana.set(thirdPhraseKana);
        this.fourthPhraseKanji.set(fourthPhraseKanji);
        this.fourthPhraseKana.set(fourthPhraseKana);
        this.fifthPhraseKanji.set(fifthPhraseKanji);
        this.fifthPhraseKana.set(fifthPhraseKana);
    }
}
