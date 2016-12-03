package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizResultViewModel;

public interface QuizResultContact {

    interface View {
        void initialize(QuizResultViewModel viewModel);

        void onRestartTraining();

        void finishTraining();
    }

    interface Actions {
        void onCreate(View view);

        void onResume();

        void onPause();

        void onClickPracticeWrongKarutas();

        void onClickBackMenu();
    }
}
