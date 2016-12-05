package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.FinishExamUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartExamUsecase;

public class ExamMasterPresenter implements ExamMasterContact.Actions {

    private final StartExamUsecase startExamUsecase;

    private final FinishExamUsecase finishExamUsecase;

    private ExamMasterContact.View view;

    public ExamMasterPresenter(@NonNull StartExamUsecase startExamUsecase,
                               @NonNull FinishExamUsecase finishExamUsecase) {
        this.startExamUsecase = startExamUsecase;
        this.finishExamUsecase = finishExamUsecase;
    }

    @Override
    public void onCreate(@NonNull ExamMasterContact.View view) {
        this.view = view;
        startExamUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::startExam);
    }

    @Override
    public void onClickGoToResult() {
        finishExamUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::navigateToResult);
    }
}
