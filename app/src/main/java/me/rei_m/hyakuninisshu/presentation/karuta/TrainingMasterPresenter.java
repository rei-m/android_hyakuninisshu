package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

public class TrainingMasterPresenter implements TrainingMasterContact.Actions {

    private final StartKarutaQuizUsecase startKarutaQuizUsecase;

    public TrainingMasterPresenter(@NonNull StartKarutaQuizUsecase startKarutaQuizUsecase) {
        this.startKarutaQuizUsecase = startKarutaQuizUsecase;
    }

    @Override
    public void onCreate(@NonNull TrainingMasterContact.View view,
                         @NonNull TrainingRange trainingRange,
                         @NonNull Kimariji kimariji) {
        startKarutaQuizUsecase.execute(trainingRange.getFromId(),
                trainingRange.getToId(),
                kimariji.getPosition()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::startTraining, throwable -> view.showEmpty());
    }
}
