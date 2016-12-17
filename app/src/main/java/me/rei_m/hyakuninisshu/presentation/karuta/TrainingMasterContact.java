package me.rei_m.hyakuninisshu.presentation.karuta;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public interface TrainingMasterContact {

    interface View {
        void startTraining();

        void showEmpty();
    }

    interface Actions {
        void onCreate(View view,
                      TrainingRange trainingRange,
                      Kimariji kimariji);
    }
}
