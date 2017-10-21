package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class QuizResultFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<String> score = new ObservableField<>("");

    public final ObservableFloat averageAnswerTime = new ObservableFloat();

    public final ObservableBoolean canRestartTraining = new ObservableBoolean();

    private final PublishSubject<Unit> restartEventSubject = PublishSubject.create();
    public final Observable<Unit> restartEvent = restartEventSubject;

    private final PublishSubject<Unit> onClickBackMenuEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickBackMenuEvent = onClickBackMenuEventSubject;

    private final PublishSubject<Unit> errorEventSubject = PublishSubject.create();
    public final Observable<Unit> errorEvent = errorEventSubject;

    private final KarutaTrainingModel karutaTrainingModel;

    private final AnalyticsManager analyticsManager;

    public QuizResultFragmentViewModel(@NonNull KarutaTrainingModel karutaTrainingModel,
                                       @NonNull AnalyticsManager analyticsManager) {
        this.karutaTrainingModel = karutaTrainingModel;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaTrainingModel.completeAggregateResultsEvent.subscribe(trainingResult -> {
            score.set(trainingResult.correctCount() + "/" + trainingResult.quizCount());
            averageAnswerTime.set(trainingResult.averageAnswerTime());
            canRestartTraining.set(trainingResult.canRestartTraining());
        }), karutaTrainingModel.completeRestartEvent.subscribe(v -> restartEventSubject.onNext(Unit.INSTANCE)));
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.QUIZ_RESULT);
        karutaTrainingModel.aggregateResults();
    }

    @SuppressWarnings("unused")
    public void onClickPracticeWrongKarutas(View view) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.RESTART_TRAINING);
        karutaTrainingModel.restartForPractice();
    }

    @SuppressWarnings("unused")
    public void onClickBackMenu(View view) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.FINISH_TRAINING);
        onClickBackMenuEventSubject.onNext(Unit.INSTANCE);
    }
}
