package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class TrainingMenuFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<TrainingRangeFrom> trainingRangeFrom = new ObservableField<>(TrainingRangeFrom.ONE);

    public final ObservableField<TrainingRangeTo> trainingRangeTo = new ObservableField<>(TrainingRangeTo.ONE_HUNDRED);

    public final ObservableField<KimarijiFilter> kimariji = new ObservableField<>(KimarijiFilter.ALL);

    public final ObservableField<KarutaStyleFilter> topPhraseStyle = new ObservableField<>(KarutaStyleFilter.KANJI);

    public final ObservableField<KarutaStyleFilter> bottomPhraseStyle = new ObservableField<>(KarutaStyleFilter.KANA);

    public final ObservableField<ColorFilter> color = new ObservableField<>(ColorFilter.ALL);

    private final PublishSubject<Unit> invalidTrainingRangeEventSubject = PublishSubject.create();
    public final Observable<Unit> invalidTrainingRangeEvent = invalidTrainingRangeEventSubject;

    private final AnalyticsManager analyticsManager;

    private final Navigator navigator;

    public TrainingMenuFragmentViewModel(@NonNull AnalyticsManager analyticsManager,
                                         @NonNull Navigator navigator) {
        this.analyticsManager = analyticsManager;
        this.navigator = navigator;
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.TRAINING_MENU);
    }

    @SuppressWarnings("unused")
    public void onClickStartTraining(View view) {

        if (trainingRangeFrom.get().ordinal() > trainingRangeTo.get().ordinal()) {
            invalidTrainingRangeEventSubject.onNext(Unit.INSTANCE);
            return;
        }

        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING);
        navigator.navigateToTrainingMaster(trainingRangeFrom.get(),
                trainingRangeTo.get(),
                kimariji.get(),
                color.get(),
                topPhraseStyle.get(),
                bottomPhraseStyle.get());
    }
}
