package me.rei_m.hyakuninisshu.viewmodel.karuta;

import android.databinding.ObservableBoolean;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsActivityViewModel;

public class TrainingExamMasterActivityViewModel extends AbsActivityViewModel {

    public final ObservableBoolean isVisibleEmpty = new ObservableBoolean(false);

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(false);

    private final PublishSubject<Unit> startTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> startTrainingEvent = startTrainingEventSubject;

    private final KarutaTrainingModel karutaTrainingModel;

    private boolean isStartedTraining = false;

    public TrainingExamMasterActivityViewModel(KarutaTrainingModel karutaTrainingModel) {
        this.karutaTrainingModel = karutaTrainingModel;
    }

    public boolean isStartedTraining() {
        return isStartedTraining;
    }

    public void onReCreate(boolean isStartedTraining) {
        this.isStartedTraining = isStartedTraining;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaTrainingModel.completeStartForExamEvent.subscribe(v -> {
            isStartedTraining = true;
            startTrainingEventSubject.onNext(Unit.INSTANCE);
        }), karutaTrainingModel.notFoundErrorEvent.subscribe(v -> {
            isVisibleEmpty.set(true);
            isVisibleAd.set(true);
        }));

        if (!isStartedTraining) {
            karutaTrainingModel.startForExam();
        }
    }

    public void onClickGoToResult() {
        isVisibleAd.set(true);
    }

    public void onRestartTraining() {
        isVisibleAd.set(false);
    }
}
