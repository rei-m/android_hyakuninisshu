package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.ExamViewModel;

public interface ExamContact {

    interface View {
        void initialize(ExamViewModel viewModel);

        void navigateToExamMaster();

        void navigateToTraining();
    }

    interface Actions {
        void onCreate(View view);

        void onResume();

        void onPause();

        void onClickStartExam();

        void onClickStartTraining();
    }
}
