package me.rei_m.hyakuninisshu.viewmodel.karuta;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsActivityViewModel;

public class TrainingMasterActivityViewModel extends AbsActivityViewModel {

    public final ObservableBoolean isVisibleEmpty = new ObservableBoolean(false);

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(false);

    private final PublishSubject<Unit> startTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> startTrainingEvent = startTrainingEventSubject;

    private final KarutaTrainingModel karutaTrainingModel;

    private TrainingRangeFrom trainingRangeFrom;
    private TrainingRangeTo trainingRangeTo;
    private Kimariji kimariji;
    private Color color;

    private boolean isStartedTraining = false;

    public TrainingMasterActivityViewModel(@NonNull KarutaTrainingModel karutaTrainingModel) {
        this.karutaTrainingModel = karutaTrainingModel;
    }

    public boolean isStartedTraining() {
        return isStartedTraining;
    }

    public void onCreate(@NonNull TrainingRangeFrom trainingRangeFrom,
                         @NonNull TrainingRangeTo trainingRangeTo,
                         @NonNull Kimariji kimariji,
                         @NonNull Color color) {
        this.trainingRangeFrom = trainingRangeFrom;
        this.trainingRangeTo = trainingRangeTo;
        this.kimariji = kimariji;
        this.color = color;
    }

    public void onReCreate(@NonNull TrainingRangeFrom trainingRangeFrom,
                           @NonNull TrainingRangeTo trainingRangeTo,
                           @NonNull Kimariji kimariji,
                           @NonNull Color color,
                           boolean isStartedTraining) {
        this.trainingRangeFrom = trainingRangeFrom;
        this.trainingRangeTo = trainingRangeTo;
        this.kimariji = kimariji;
        this.color = color;
        this.isStartedTraining = isStartedTraining;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaTrainingModel.completeStartEvent.subscribe(v -> {
            isStartedTraining = true;
            isVisibleEmpty.set(false);
            isVisibleAd.set(false);
            startTrainingEventSubject.onNext(Unit.INSTANCE);
        }), karutaTrainingModel.notFoundErrorEvent.subscribe(v -> {
            isVisibleEmpty.set(true);
            isVisibleAd.set(true);
        }));

        if (!isStartedTraining) {
            karutaTrainingModel.start(trainingRangeFrom.getId(),
                    trainingRangeTo.getId(),
                    kimariji.getPosition(),
                    color.getCode());
        }
    }

    public void onClickGoToResult() {
        isVisibleAd.set(true);
    }

    public void onRestartTraining() {
        isVisibleAd.set(false);
    }
}
