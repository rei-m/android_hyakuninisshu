package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;

public class TrainingMenuPresenter implements TrainingMenuContact.Actions {

    private final AnalyticsManager analyticsManager;

    private TrainingMenuContact.View view;

    public TrainingMenuPresenter(@NonNull AnalyticsManager analyticsManager) {
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onCreate(@NonNull TrainingMenuContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.TRAINING_MENU);
    }

    @Override
    public void onClickStartTraining(@NonNull TrainingRange trainingRange,
                                     @NonNull Kimariji kimariji,
                                     @NonNull KarutaStyle topPhraseStyle,
                                     @NonNull KarutaStyle bottomPhraseStyle) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING);
        view.navigateToTraining(trainingRange, kimariji, topPhraseStyle, bottomPhraseStyle);
    }
}
