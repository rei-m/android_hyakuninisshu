package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizResultUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.RestartWrongKarutaQuizUsecase;

public class QuizResultPresenter implements QuizResultContact.Actions {

    private final DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase;

    private final RestartWrongKarutaQuizUsecase restartWrongKarutaQuizUsecase;

    private QuizResultContact.View view;

    public QuizResultPresenter(DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase,
                               RestartWrongKarutaQuizUsecase restartWrongKarutaQuizUsecase) {
        this.displayKarutaQuizResultUsecase = displayKarutaQuizResultUsecase;
        this.restartWrongKarutaQuizUsecase = restartWrongKarutaQuizUsecase;
    }

    @Override
    public void onCreate(@NonNull QuizResultContact.View view) {
        this.view = view;
    }

    @Override
    public void onCreateView() {
        displayKarutaQuizResultUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::initialize);
    }

    @Override
    public void onClickPracticeWrongKarutas() {
        restartWrongKarutaQuizUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onRestartTraining);
    }

    @Override
    public void onClickBackMenu() {
        view.finishTraining();
    }
}
