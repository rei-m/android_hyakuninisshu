package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.QuizState;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;

public class QuizPresenter implements QuizContact.Actions {

    private QuizContact.View view;

    private final DisplayKarutaQuizUsecase displayKarutaQuizUsecase;

    private final AnswerKarutaQuizUsecase answerKarutaQuizUsecase;

    public QuizPresenter(@NonNull DisplayKarutaQuizUsecase displayKarutaQuizUsecase,
                         @NonNull AnswerKarutaQuizUsecase answerKarutaQuizUsecase) {
        this.displayKarutaQuizUsecase = displayKarutaQuizUsecase;
        this.answerKarutaQuizUsecase = answerKarutaQuizUsecase;
    }

    @Override
    public void onCreate(@NonNull QuizContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume(@Nullable QuizViewModel viewModel,
                         @NonNull KarutaStyle topPhraseStyle,
                         @NonNull KarutaStyle bottomPhraseStyle,
                         int selectedChoiceNo,
                         @NonNull QuizState state) {

        if (viewModel == null || state == QuizState.UNANSWERED) {
            displayKarutaQuizUsecase.execute(topPhraseStyle, bottomPhraseStyle)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(quizViewModel -> {
                        view.initialize(quizViewModel);
                        view.startDisplayQuizAnimation(quizViewModel);
                    });
        } else {
            view.initialize(viewModel);
            view.displayResult(selectedChoiceNo, state == QuizState.ANSWERED_COLLECT);
        }
    }

    @Override
    public void onClickChoice(@NonNull String quizId, int choiceNo) {
        answerKarutaQuizUsecase.execute(quizId, choiceNo).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(isCollect -> {
                    view.displayResult(choiceNo, isCollect);
                });
    }

    @Override
    public void onClickResult(@NonNull String quizId) {
        view.displayAnswer(quizId);
    }
}
