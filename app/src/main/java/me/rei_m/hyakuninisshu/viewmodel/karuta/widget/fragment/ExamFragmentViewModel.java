package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.view.View;

import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class ExamFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableBoolean hasResult = new ObservableBoolean(false);

    public final ObservableField<String> score = new ObservableField<>("");

    public final ObservableFloat averageAnswerTime = new ObservableFloat();

    private final KarutaExamModel karutaExamModel;

    private final Navigator navigator;

    private final AnalyticsManager analyticsManager;

    public ExamFragmentViewModel(@NonNull KarutaExamModel karutaExamModel,
                                 @NonNull Navigator navigator,
                                 @NonNull AnalyticsManager analyticsManager) {
        this.karutaExamModel = karutaExamModel;
        this.navigator = navigator;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaExamModel.completeFetchResultEvent.subscribe(examResult -> {
            hasResult.set(true);
            score.set(examResult.score());
            averageAnswerTime.set(examResult.averageAnswerTime());
        }), karutaExamModel.norFoundResultEvent.subscribe(v -> {
            hasResult.set(false);
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM);
        karutaExamModel.fetchRecentResult();
    }

    @SuppressWarnings("unused")
    public void onClickStartExam(View view) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_EXAM);
        navigator.navigateToExamMaster();
    }

    @SuppressWarnings("unused")
    public void onClickStartTraining(View view) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING_FOR_EXAM);
        navigator.navigateToExamTrainingMaster();
    }
}
