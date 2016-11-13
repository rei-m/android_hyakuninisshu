package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;

public interface QuizContact {

    interface View {
        void initialize(QuizViewModel viewModel);

        void displayAnswer(String quizId, boolean isCollect);
    }

    interface Actions {
        void onCreate(View view);
        
        void onResume();

        void onClickChoice(String quizId, int choiceNo);
    }
}
