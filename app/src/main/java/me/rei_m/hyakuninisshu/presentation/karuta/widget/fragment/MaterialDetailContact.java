package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialDetailViewModel;

public interface MaterialDetailContact {

    interface View {
        void initialize(MaterialDetailViewModel viewModel);
    }

    interface Actions {
        void onCreate(View view);

        void onResume(int karutaNo);

        void onPause();
    }
}
