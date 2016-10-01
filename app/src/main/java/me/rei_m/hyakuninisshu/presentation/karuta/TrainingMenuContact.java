package me.rei_m.hyakuninisshu.presentation.karuta;

public interface TrainingMenuContact {

    interface Actions {

        void onCreate(View view);

        void onClickStartTraining();
    }

    interface View {

        void navigateToTraining();
    }
}
