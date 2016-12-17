package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayExamUsecase;

public class ExamPresenter implements ExamContact.Actions {

    private final DisplayExamUsecase displayExamUsecase;

    private ExamContact.View view;

    private CompositeDisposable disposable;

    public ExamPresenter(@NonNull DisplayExamUsecase displayExamUsecase) {
        this.displayExamUsecase = displayExamUsecase;
    }

    @Override
    public void onCreate(ExamContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume() {
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
        view.navigateToExamMaster();
    }
}
