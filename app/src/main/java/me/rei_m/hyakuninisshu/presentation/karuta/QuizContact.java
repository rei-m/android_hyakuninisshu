package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;

public interface QuizContact {

    interface View {
        void initialize(QuizViewModel viewModel);

        void displayAnswer(String quizId);
    }

    interface Actions {
        void onCreate(View view, @Nullable Bundle savedInstanceState);

        void onCreateView(@Nullable Bundle savedInstanceState);

        void onClickChoice(String quizId, int choiceNo);
    }
}
