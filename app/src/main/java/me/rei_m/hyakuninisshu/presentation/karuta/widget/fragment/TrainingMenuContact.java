package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;

public interface TrainingMenuContact {

    interface Actions {

        void onCreate(View view);

        void onResume();

        void onClickStartTraining(@NonNull TrainingRangeFrom trainingRangeFrom,
                                  @NonNull TrainingRangeTo trainingRangeTo,
                                  @NonNull Kimariji kimariji,
                                  @NonNull Color color,
                                  @NonNull KarutaStyle topPhraseStyle,
                                  @NonNull KarutaStyle bottomPhraseStyle);
    }

    interface View {

        void showInvalidTrainingRangeMessage();

        void navigateToTraining(@NonNull TrainingRangeFrom trainingRangeFrom,
                                @NonNull TrainingRangeTo trainingRangeTo,
                                @NonNull Kimariji kimariji,
                                @NonNull Color color,
                                @NonNull KarutaStyle topPhraseStyle,
                                @NonNull KarutaStyle bottomPhraseStyle);
    }
}
