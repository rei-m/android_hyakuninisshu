package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
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

        void onDestroyView();

        void onOptionItemSelected(@NonNull Color color);
    }
}
