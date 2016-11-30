package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.databinding.ObservableField;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public class TrainingMenuViewModel {

    private final ObservableField<TrainingRange> trainingRange;

    private final ObservableField<Kimariji> kimariji;

    private final ObservableField<KarutaStyle> topPhraseStyle;

    private final ObservableField<KarutaStyle> bottomPhraseStyle;

    public TrainingMenuViewModel(TrainingRange trainingRange,
                                 Kimariji kimariji,
                                 KarutaStyle topPhraseStyle,
                                 KarutaStyle bottomPhraseStyle) {
        this.trainingRange = new ObservableField<>();
        this.trainingRange.set(trainingRange);

        this.kimariji = new ObservableField<>();
        this.kimariji.set(kimariji);

        this.topPhraseStyle = new ObservableField<>();
        this.topPhraseStyle.set(topPhraseStyle);

        this.bottomPhraseStyle = new ObservableField<>();
        this.bottomPhraseStyle.set(bottomPhraseStyle);
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
}
