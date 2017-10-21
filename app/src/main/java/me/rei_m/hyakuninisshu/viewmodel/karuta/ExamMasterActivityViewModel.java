package me.rei_m.hyakuninisshu.viewmodel.karuta;

import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsActivityViewModel;

public class ExamMasterActivityViewModel extends AbsActivityViewModel {

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(false);

    private final PublishSubject<Unit> startExamEventSubject = PublishSubject.create();
    public final Observable<Unit> startExamEvent = startExamEventSubject;

    private final PublishSubject<Long> aggregateExamResultsEventSubject = PublishSubject.create();
    public final Observable<Long> aggregateExamResultsEvent = aggregateExamResultsEventSubject;

    private final KarutaExamModel karutaExamModel;

    private boolean isStartedExam = false;
    private boolean isFinishedExam = false;

    public ExamMasterActivityViewModel(@NonNull KarutaExamModel karutaExamModel) {
        this.karutaExamModel = karutaExamModel;
    }

    public boolean isStartedExam() {
        return isStartedExam;
    }

    public boolean isFinishedExam() {
        return isFinishedExam;
    }

    public void onReCreate(boolean isStartedExam,
                           boolean isFinishedExam) {
        this.isStartedExam = isStartedExam;
        this.isFinishedExam = isFinishedExam;
        this.isVisibleAd.set(isFinishedExam);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaExamModel.completeStartEvent.subscribe(v -> {
            isStartedExam = true;
            isVisibleAd.set(false);
            startExamEventSubject.onNext(Unit.INSTANCE);
        }), karutaExamModel.completeAggregateResultsEvent.subscribe(karutaExamId -> {
            isFinishedExam = true;
            isVisibleAd.set(true);
            aggregateExamResultsEventSubject.onNext(karutaExamId.value());
        }));

        if (!isStartedExam) {
            karutaExamModel.start();
        }
    }

    public void onClickGoToResult() {
        karutaExamModel.aggregateResults();
    }
}
