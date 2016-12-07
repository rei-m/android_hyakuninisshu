package me.rei_m.hyakuninisshu.presentation.karuta;

public interface ExamMasterContact {

    interface View {
        void startExam();

        void navigateToResult(Long examId);
    }

    interface Actions {
        void onCreate(View view);

        void onClickGoToResult();
    }
}
