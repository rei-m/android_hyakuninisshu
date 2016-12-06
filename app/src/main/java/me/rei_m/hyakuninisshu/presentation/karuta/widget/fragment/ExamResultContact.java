package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.ExamResultViewModel;

public interface ExamResultContact {

    interface View {
        void initialize(ExamResultViewModel viewModel);

        void finishExam();
    }

    interface Actions {
        void onCreate(View view);

        void onResume();

        void onPause();

        void onClickBackMenu();
    }
}
