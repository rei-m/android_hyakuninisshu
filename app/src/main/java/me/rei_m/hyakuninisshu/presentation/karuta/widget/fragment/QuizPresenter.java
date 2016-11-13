package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;

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
    public void onCreate(@NonNull QuizContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        displayKarutaQuizUsecase.execute().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::initialize);
    }

    @Override
    public void onClickChoice(String quizId, int choiceNo) {
        answerKarutaQuizUsecase.execute(quizId, choiceNo).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(isCollect -> {
                    view.displayAnswer(quizId, isCollect);
                });
    }
}
