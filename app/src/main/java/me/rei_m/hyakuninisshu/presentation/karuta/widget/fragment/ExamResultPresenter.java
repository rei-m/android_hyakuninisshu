package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaExamResultUsecase;

public class ExamResultPresenter implements ExamResultContact.Actions {

    private final DisplayKarutaExamResultUsecase displayKarutaExamResultUsecase;

    private final AnalyticsManager analyticsManager;

    private ExamResultContact.View view;

    private CompositeDisposable disposable;

    public ExamResultPresenter(@NonNull DisplayKarutaExamResultUsecase displayKarutaExamResultUsecase,
                               @NonNull AnalyticsManager analyticsManager) {
        this.displayKarutaExamResultUsecase = displayKarutaExamResultUsecase;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onCreate(@NonNull ExamResultContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume(@NonNull Long examId) {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM_RESULT);
        disposable = new CompositeDisposable();
        disposable.add(displayKarutaExamResultUsecase.execute(examId)
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
    public void onClickBackMenu() {
        view.finishExam();
    }
}
