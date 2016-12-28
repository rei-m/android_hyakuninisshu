package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.QuizState;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;

public class QuizPresenter implements QuizContact.Actions {

    private final DisplayKarutaQuizUsecase displayKarutaQuizUsecase;

    private final AnswerKarutaQuizUsecase answerKarutaQuizUsecase;

    private QuizContact.View view;

    private CompositeDisposable disposable;

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

        disposable = new CompositeDisposable();

        if (viewModel == null || state == QuizState.UNANSWERED) {
            disposable.add(displayKarutaQuizUsecase.execute(topPhraseStyle, bottomPhraseStyle)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(quizViewModel -> {
                        view.initialize(quizViewModel);
                        view.startDisplayQuizAnimation(quizViewModel);
                    }, e -> {
                        view.displayError();
                    }));
        } else {
            view.initialize(viewModel);
            view.displayResult(selectedChoiceNo, state == QuizState.ANSWERED_COLLECT);
        }
    }

    @Override
    public void onPause() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    public void onClickChoice(@NonNull String quizId, int choiceNo) {
        disposable.add(answerKarutaQuizUsecase.execute(quizId, choiceNo)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isCollect -> {
                    view.displayResult(choiceNo, isCollect);
                }));
    }

    @Override
    public void onClickResult(@NonNull String quizId) {
        view.displayAnswer(quizId);
    }
}
