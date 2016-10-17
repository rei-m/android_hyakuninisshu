package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuizMasterPresenter implements QuizMasterContact.Actions {

    private QuizMasterContact.View view;

    private StartKarutaQuizUsecase startKarutaQuizUsecase;

    public QuizMasterPresenter(StartKarutaQuizUsecase startKarutaQuizUsecase) {
        this.startKarutaQuizUsecase = startKarutaQuizUsecase;
    }

    @Override
    public void onCreate(@NonNull QuizMasterContact.View view) {
        this.view = view;
        this.startKarutaQuizUsecase.execute(10).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(aVoid -> {
            view.startQuiz();
        });
    }
}
