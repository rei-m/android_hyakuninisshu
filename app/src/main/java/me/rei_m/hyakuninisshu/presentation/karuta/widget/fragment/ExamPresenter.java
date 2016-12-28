package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayExamUsecase;

public class ExamPresenter implements ExamContact.Actions {

    private final DisplayExamUsecase displayExamUsecase;

    private final AnalyticsManager analyticsManager;

    private ExamContact.View view;

    private CompositeDisposable disposable;

    public ExamPresenter(@NonNull DisplayExamUsecase displayExamUsecase,
                         @NonNull AnalyticsManager analyticsManager) {
        this.displayExamUsecase = displayExamUsecase;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onCreate(ExamContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM);
        disposable = new CompositeDisposable();
        disposable.add(displayExamUsecase.execute()
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
    public void onClickStartExam() {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_EXAM);
        view.navigateToExamMaster();
    }

    @Override
    public void onClickStartTraining() {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING_FOR_EXAM);
        view.navigateToTraining();
    }
}
