package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialViewModel;

public interface MaterialContact {

    interface View {
        void initialize(MaterialViewModel viewModel);
    }

    interface Actions {
        void onCreate(View view);

        void onResume();

        void onPause();
    }
}
