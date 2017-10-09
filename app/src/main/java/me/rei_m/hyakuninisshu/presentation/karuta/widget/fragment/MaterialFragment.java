package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialBinding;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialKarutaListAdapter;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialFragmentViewModel;

public class MaterialFragment extends Fragment {

    public static final String TAG = "MaterialFragment";

    public static MaterialFragment newInstance() {
        return new MaterialFragment();
    }

    @Inject
    Navigator navigator;

    @Inject
    MaterialFragmentViewModel viewModel;

    @Inject
    MaterialKarutaListAdapter.Injector injector;

    private FragmentMaterialBinding binding;

    public MaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMaterialBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        MaterialKarutaListAdapter adapter = new MaterialKarutaListAdapter(viewModel.karutaList, injector);
        binding.recyclerKarutaList.setAdapter(adapter);
        binding.recyclerKarutaList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
        viewModel = null;
        injector = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        for (Color color : Color.values()) {
            MenuItem menuItem = menu.add(Menu.NONE, color.ordinal(), Menu.NONE, color.getLabel(getResources()));
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menuItem.setOnMenuItemClickListener(menuColor -> {
                viewModel.onOptionItemSelected(color);
                return false;
            });
        }
    }
}
