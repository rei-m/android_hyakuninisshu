package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public class TrainingMenuPresenter implements TrainingMenuContact.Actions {

    private TrainingMenuContact.View view;

    @Override
    public void onCreate(@NonNull TrainingMenuContact.View view) {
        this.view = view;
    }

    @Override
    public void onClickStartTraining(@NonNull TrainingRange trainingRange,
                                     @NonNull Kimariji kimariji,
                                     @NonNull KarutaStyle topPhraseStyle,
                                     @NonNull KarutaStyle bottomPhraseStyle) {
        view.navigateToTraining(trainingRange, kimariji, topPhraseStyle, bottomPhraseStyle);
    }
}
