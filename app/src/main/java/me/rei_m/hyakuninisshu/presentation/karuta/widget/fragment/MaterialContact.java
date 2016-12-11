package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialKarutaListAdapter;

public interface MaterialContact {

    interface View {
        void initialize(MaterialViewModel viewModel);

        void navigateToMaterialDetail(int karutaNo);
    }

    interface Actions extends MaterialKarutaListAdapter.OnRecyclerViewInteractionListener {
        void onCreate(View view);

        void onResume();

        void onPause();
    }
}
