package me.rei_m.hyakuninisshu.presentation.karuta;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizResultViewModel;

public interface QuizResultContact {

    interface View {
        void initialize(QuizResultViewModel viewModel);
    }

    interface Actions {
        void onCreate(View view);

        void onCreateView();
    }
}
