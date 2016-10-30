package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.databinding.ObservableField;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public class TrainingMenuViewModel {

    private ObservableField<TrainingRange> trainingRange;

    private ObservableField<Kimariji> kimariji;

    public TrainingMenuViewModel() {
        trainingRange = new ObservableField<>();
        kimariji = new ObservableField<>();
    }

    public ObservableField<TrainingRange> getTrainingRange() {
        return trainingRange;
    }

    public ObservableField<Kimariji> getKimariji() {
        return kimariji;
    }
}
