package me.rei_m.hyakuninisshu.presentation.karuta;

public interface TrainingMenuContact {

    interface Actions {

        void onCreate(View view);

        void onClickStartTraining();

        void onQuestionRangeItemSelected(int position);

        void onKimarijiItemSelected(int position);
    }

    interface View {

        void navigateToTraining();
    }
}
