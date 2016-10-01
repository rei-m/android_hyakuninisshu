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
        view.navigateToTraining();
    }
}
