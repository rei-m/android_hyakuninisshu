package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;

public interface QuizAnswerContact {

    interface View {
        void initialize(QuizAnswerViewModel viewModel);

        void goToNext();

        void goToResult();

        void displayError();
    }

    interface Actions {
        void onCreate(View view);

        void onResume(String quizId);

        void onPause();

        void onClickNextQuiz();

        void onClickConfirmResult();
    }
}
