package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public class TrainingMenuViewModel {

    private final ObservableField<TrainingRange> trainingRange;

    private final ObservableField<Kimariji> kimariji;

    private final ObservableField<KarutaStyle> topPhraseStyle;

    private final ObservableField<KarutaStyle> bottomPhraseStyle;

    private final ObservableField<Color> color;

    public TrainingMenuViewModel(@NonNull TrainingRange trainingRange,
                                 @NonNull Kimariji kimariji,
                                 @NonNull KarutaStyle topPhraseStyle,
                                 @NonNull KarutaStyle bottomPhraseStyle,
                                 @NonNull Color color) {

        this.trainingRange = new ObservableField<>();
        this.trainingRange.set(trainingRange);

        this.kimariji = new ObservableField<>();
        this.kimariji.set(kimariji);

        this.topPhraseStyle = new ObservableField<>();
        this.topPhraseStyle.set(topPhraseStyle);

        this.bottomPhraseStyle = new ObservableField<>();
        this.bottomPhraseStyle.set(bottomPhraseStyle);

        this.color = new ObservableField<>();
        this.color.set(color);
    }

    public ObservableField<TrainingRange> getTrainingRange() {
        return trainingRange;
    }

    public ObservableField<Kimariji> getKimariji() {
        return kimariji;
    }

    public ObservableField<KarutaStyle> getTopPhraseStyle() {
        return topPhraseStyle;
    }

    public ObservableField<KarutaStyle> getBottomPhraseStyle() {
        return bottomPhraseStyle;
    }

    public ObservableField<Color> getColor() {
        return color;
    }
}
