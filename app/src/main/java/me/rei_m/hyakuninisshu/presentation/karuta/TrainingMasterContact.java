package me.rei_m.hyakuninisshu.presentation.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public interface TrainingMasterContact {

    interface View {
        void startTraining();

        void showEmpty();
    }

    interface Actions {
        void onCreate(@NonNull View view,
                      @NonNull TrainingRange trainingRange,
                      @NonNull Kimariji kimariji,
                      @NonNull Color color);

        void onDestroy();
    }
}
