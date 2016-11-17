package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public class TrainingMenuPresenter implements TrainingMenuContact.Actions {

    private TrainingMenuContact.View view;

    @Override
    public void onCreate(@NonNull TrainingMenuContact.View view) {
        this.view = view;
    }

    @Override
    public void onClickStartTraining(TrainingRange trainingRange, Kimariji kimariji) {
        view.navigateToTraining(trainingRange, kimariji);
    }
}
