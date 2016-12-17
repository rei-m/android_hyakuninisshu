package me.rei_m.hyakuninisshu.presentation.karuta;

public interface TrainingExamMasterContact {

    interface View {
        void startTraining();

        void showEmpty();
    }

    interface Actions {
        void onCreate(View view);
    }
}
