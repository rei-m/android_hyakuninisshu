package me.rei_m.hyakuninisshu.presentation.karuta;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public interface TrainingMenuContact {

    interface Actions {

        void onCreate(View view);

        void onClickStartTraining(TrainingRange trainingRange, Kimariji kimariji);
    }

    interface View {

        void navigateToTraining(TrainingRange trainingRange, Kimariji kimariji);
    }
}
