package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.QuizState;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;

public interface QuizContact {

    interface View {
        void initialize(@NonNull QuizViewModel viewModel);

        void startDisplayQuizAnimation(@NonNull QuizViewModel viewModel);

        void displayResult(int selectedChoiceNo, boolean isCollect);

        void displayAnswer(@NonNull String quizId);

        void displayError();
    }

    interface Actions {
        void onCreate(@NonNull View view);

        void onResume(@Nullable QuizViewModel viewModel,
                      @NonNull KarutaStyle topPhraseStyle,
                      @NonNull KarutaStyle bottomPhraseStyle,
                      int selectedChoiceNo,
                      @NonNull QuizState state);

        void onPause();

        void onClickChoice(@NonNull String quizId, int choiceNo);

        void onClickResult(@NonNull String quizId);
    }
}
