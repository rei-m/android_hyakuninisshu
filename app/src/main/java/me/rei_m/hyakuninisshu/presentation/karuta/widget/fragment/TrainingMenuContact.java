package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public interface TrainingMenuContact {

    interface Actions {

        void onCreate(View view);

        void onClickStartTraining(TrainingRange trainingRange,
                                  Kimariji kimariji,
                                  KarutaStyle topPhraseStyle,
                                  KarutaStyle bottomPhraseStyle);
    }

    interface View {

        void navigateToTraining(TrainingRange trainingRange,
                                Kimariji kimariji,
                                KarutaStyle topPhraseStyle,
                                KarutaStyle bottomPhraseStyle);
    }
}
