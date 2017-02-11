package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
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
    public void onClickStartTraining(@NonNull TrainingRangeFrom trainingRangeFrom,
                                     @NonNull TrainingRangeTo trainingRangeTo,
                                     @NonNull Kimariji kimariji,
                                     @NonNull Color color,
                                     @NonNull KarutaStyle topPhraseStyle,
                                     @NonNull KarutaStyle bottomPhraseStyle) {

        if (trainingRangeFrom.ordinal() > trainingRangeTo.ordinal()) {
            view.showInvalidTrainingRangeMessage();
            return;
        }

        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING);
        view.navigateToTraining(trainingRangeFrom, trainingRangeTo, kimariji, color, topPhraseStyle, bottomPhraseStyle);
    }
}
