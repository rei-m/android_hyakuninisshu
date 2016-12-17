package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.StartTrainingForKarutaExamUsecase;

public class TrainingExamMasterPresenter implements TrainingExamMasterContact.Actions {

    private final StartTrainingForKarutaExamUsecase startTrainingForKarutaExamUsecase;

    public TrainingExamMasterPresenter(@NonNull StartTrainingForKarutaExamUsecase startTrainingForKarutaExamUsecase) {
        this.startTrainingForKarutaExamUsecase = startTrainingForKarutaExamUsecase;
    }

    @Override
    public void onCreate(@NonNull TrainingExamMasterContact.View view) {
        startTrainingForKarutaExamUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::startTraining, throwable -> view.showEmpty());
    }
}
