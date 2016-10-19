package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.KarutaQuizViewModel;

public interface QuizMasterContact {

    interface View {
        void startQuiz(KarutaQuizViewModel viewModel);
    }

    interface Actions {
        void onCreate(View view, Bundle savedInstanceState);
    }
}
