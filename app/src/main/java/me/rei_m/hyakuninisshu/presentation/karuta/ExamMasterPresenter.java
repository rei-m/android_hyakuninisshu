package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.FinishKarutaExamUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaExamUsecase;

public class ExamMasterPresenter implements ExamMasterContact.Actions {

    private final StartKarutaExamUsecase startKarutaExamUsecase;

    private final FinishKarutaExamUsecase finishKarutaExamUsecase;

    private ExamMasterContact.View view;

    public ExamMasterPresenter(@NonNull StartKarutaExamUsecase startKarutaExamUsecase,
                               @NonNull FinishKarutaExamUsecase finishKarutaExamUsecase) {
        this.startKarutaExamUsecase = startKarutaExamUsecase;
        this.finishKarutaExamUsecase = finishKarutaExamUsecase;
    }

    @Override
    public void onCreate(@NonNull ExamMasterContact.View view) {
        this.view = view;
        startKarutaExamUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::startExam);
    }

    @Override
    public void onClickGoToResult() {
        finishKarutaExamUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::navigateToResult);
    }
}
