package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

public interface ExamContact {

    interface View {
        void navigateToExamMaster();
    }

    interface Actions {
        void onCreate(View view);

        void onClickStartExam();
    }
}
