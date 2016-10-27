package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuizPresenter implements QuizContact.Actions {

    private QuizContact.View view;

    private final DisplayKarutaQuizUsecase displayKarutaQuizUsecase;

    private final AnswerKarutaQuizUsecase answerKarutaQuizUsecase;

    public QuizPresenter(DisplayKarutaQuizUsecase displayKarutaQuizUsecase,
                         AnswerKarutaQuizUsecase answerKarutaQuizUsecase) {
        this.displayKarutaQuizUsecase = displayKarutaQuizUsecase;
        this.answerKarutaQuizUsecase = answerKarutaQuizUsecase;
    }

    @Override
    public void onCreate(@NonNull QuizContact.View view,
                         @Nullable Bundle savedInstanceState) {
        this.view = view;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        displayKarutaQuizUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::startQuiz);
    }

    @Override
    public void onClickChoice(String quizId, int choiceNo) {
        answerKarutaQuizUsecase.execute(quizId, choiceNo).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(isCollect -> {
                    view.displayAnswer(quizId, isCollect);
                });
    }
}
