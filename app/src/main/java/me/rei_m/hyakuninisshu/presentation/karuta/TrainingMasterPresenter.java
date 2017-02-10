package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

public class TrainingMasterPresenter implements TrainingMasterContact.Actions {

    private final StartKarutaQuizUsecase startKarutaQuizUsecase;

    private CompositeDisposable disposable;

    public TrainingMasterPresenter(@NonNull StartKarutaQuizUsecase startKarutaQuizUsecase) {
        this.startKarutaQuizUsecase = startKarutaQuizUsecase;
    }

    @Override
    public void onCreate(@NonNull TrainingMasterContact.View view,
                         @NonNull TrainingRange trainingRange,
                         @NonNull Kimariji kimariji,
                         @NonNull Color color) {
        disposable = new CompositeDisposable();
        disposable.add(startKarutaQuizUsecase.execute(trainingRange.getFromId(),
                trainingRange.getToId(),
                kimariji.getPosition(),
                color.getCode()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::startTraining, throwable -> view.showEmpty()));
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
