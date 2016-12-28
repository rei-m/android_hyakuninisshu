package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizResultUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.RestartWrongKarutaQuizUsecase;

public class QuizResultPresenter implements QuizResultContact.Actions {

    private final DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase;

    private final RestartWrongKarutaQuizUsecase restartWrongKarutaQuizUsecase;

    private final AnalyticsManager analyticsManager;

    private QuizResultContact.View view;

    private CompositeDisposable disposable;

    public QuizResultPresenter(@NonNull DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase,
                               @NonNull RestartWrongKarutaQuizUsecase restartWrongKarutaQuizUsecase,
                               @NonNull AnalyticsManager analyticsManager) {
        this.displayKarutaQuizResultUsecase = displayKarutaQuizResultUsecase;
        this.restartWrongKarutaQuizUsecase = restartWrongKarutaQuizUsecase;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onCreate(@NonNull QuizResultContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.QUIZ_RESULT);
        disposable = new CompositeDisposable();
        disposable.add(displayKarutaQuizResultUsecase.execute()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::initialize));
    }

    @Override
    public void onPause() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    public void onClickPracticeWrongKarutas() {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.RESTART_TRAINING);
        disposable.add(restartWrongKarutaQuizUsecase.execute()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onRestartTraining));
    }

    @Override
    public void onClickBackMenu() {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.FINISH_TRAINING);
        view.finishTraining();
    }
}
