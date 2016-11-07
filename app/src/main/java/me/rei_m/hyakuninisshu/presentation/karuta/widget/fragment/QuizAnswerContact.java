package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;

public interface QuizAnswerContact {

    interface View {
        void initialize(QuizAnswerViewModel viewModel);

        void goToNext();

        void goToResult();
    }

    interface Actions {
        void onCreate(View view);

        void onCreateView(String quizId);

        void onClickNextQuiz();

        void onClickConfirmResult();
    }
}
