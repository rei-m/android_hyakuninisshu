package me.rei_m.hyakuninisshu.presentation.karuta;

public interface QuizMasterContact {

    interface View {
        void startQuiz();
    }

    interface Actions {
        void onCreate(View view);
    }
}
