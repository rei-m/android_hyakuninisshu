package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

public class TrainingMenuPresenter implements TrainingMenuContact.Actions {

    private TrainingMenuContact.View view;

    @Override
    public void onCreate(@NonNull TrainingMenuContact.View view) {
        this.view = view;
    }

    @Override
    public void onClickStartTraining() {
        System.out.println("hogehoge");
        view.navigateToTraining();
    }

    @Override
    public void onQuestionRangeItemSelected(int position) {
        System.out.println(position);
    }

    @Override
    public void onKimarijiItemSelected(int position) {
        System.out.println(position);
    }
}
