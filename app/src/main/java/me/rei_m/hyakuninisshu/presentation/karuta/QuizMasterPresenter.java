package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuizMasterPresenter implements QuizMasterContact.Actions {

    private final StartKarutaQuizUsecase startKarutaQuizUsecase;

    public QuizMasterPresenter(@NonNull StartKarutaQuizUsecase startKarutaQuizUsecase) {
        this.startKarutaQuizUsecase = startKarutaQuizUsecase;
    }

    @Override
    public void onCreate(@NonNull QuizMasterContact.View view,
                         @NonNull TrainingRange trainingRange,
                         @NonNull Kimariji kimariji) {
        startKarutaQuizUsecase.execute(trainingRange.getFromId(),
                trainingRange.getToId(),
                kimariji.getPosition()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {
                    view.startQuiz();
                }, throwable -> {
                    view.showEmpty();
                });
    }
}
