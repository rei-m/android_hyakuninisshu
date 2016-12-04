package me.rei_m.hyakuninisshu.presentation.karuta;

public interface ExamMasterContact {

    interface View {
        void startExam();
    }

    interface Actions {
        void onCreate(View view);

        void onClickGoToResult();
    }
}
