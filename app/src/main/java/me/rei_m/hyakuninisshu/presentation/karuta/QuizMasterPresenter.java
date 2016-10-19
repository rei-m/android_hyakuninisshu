package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuizMasterPresenter implements QuizMasterContact.Actions {

    private QuizMasterContact.View view;

    private StartKarutaQuizUsecase startKarutaQuizUsecase;

    private DisplayKarutaQuizUsecase displayKarutaQuizUsecase;

    public QuizMasterPresenter(StartKarutaQuizUsecase startKarutaQuizUsecase,
                               DisplayKarutaQuizUsecase displayKarutaQuizUsecase) {
        this.startKarutaQuizUsecase = startKarutaQuizUsecase;
        this.displayKarutaQuizUsecase = displayKarutaQuizUsecase;
    }

    @Override
    public void onCreate(@NonNull QuizMasterContact.View view,
                         Bundle savedInstanceState) {
        this.view = view;

        if (savedInstanceState == null) {
            startKarutaQuizUsecase.execute(10).concatMap(aVoid -> displayKarutaQuizUsecase.execute())
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(view::startQuiz);
        }
    }
}
