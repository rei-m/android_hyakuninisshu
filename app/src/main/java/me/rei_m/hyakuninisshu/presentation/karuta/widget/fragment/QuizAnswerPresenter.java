package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizAnswerUsecase;

public class QuizAnswerPresenter implements QuizAnswerContact.Actions {

    private final DisplayKarutaQuizAnswerUsecase displayKarutaQuizAnswerUsecase;

    private QuizAnswerContact.View view;

    public QuizAnswerPresenter(DisplayKarutaQuizAnswerUsecase displayKarutaQuizAnswerUsecase) {
        this.displayKarutaQuizAnswerUsecase = displayKarutaQuizAnswerUsecase;
    }

    @Override
    public void onCreate(@NonNull QuizAnswerContact.View view) {
        this.view = view;
    }

    @Override
    public void onCreateView(String quizId) {
        displayKarutaQuizAnswerUsecase.execute(quizId).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::initialize);
    }

    @Override
    public void onClickNextQuiz() {
        view.goToNext();
    }

    @Override
    public void onClickConfirmResult() {
        view.goToResult();
    }
}