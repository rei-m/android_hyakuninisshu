package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.QuizState;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;

public interface QuizContact {

    interface View {
        void initialize(QuizViewModel viewModel);

        void displayResult(String quizId, boolean isCollect);

        void displayAnswer(String quizId);
    }

    interface Actions {
        void onCreate(View view);

        void onResume(QuizState state);

        void onClickChoice(String quizId, int choiceNo);

        void onClickResult(String quizId);
    }
}
