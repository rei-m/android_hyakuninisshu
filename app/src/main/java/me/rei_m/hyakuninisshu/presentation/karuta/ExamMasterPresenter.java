package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.StartExamUsecase;

public class ExamMasterPresenter implements ExamMasterContact.Actions {

    private final StartExamUsecase startExamUsecase;

    public ExamMasterPresenter(@NonNull StartExamUsecase startExamUsecase) {
        this.startExamUsecase = startExamUsecase;
    }

    @Override
    public void onCreate(@NonNull ExamMasterContact.View view) {
        startExamUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::startExam);
    }

    @Override
    public void onClickGoToResult() {
        
    }
}
