/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialKarutaListAdapter;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.adapter.di.KarutaListItemViewModelModule;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.MaterialFragmentViewModelModule;

public class MaterialFragment extends DaggerFragment {

    public static final String TAG = MaterialFragment.class.getSimpleName();

    public static MaterialFragment newInstance() {
        return new MaterialFragment();
    }

    @Inject
    AnalyticsManager analyticsManager;

    @Inject
    Navigator navigator;

    @Inject
    MaterialFragmentViewModel.Factory viewModelFactory;

    @Inject
    MaterialKarutaListAdapter.Injector adapterInjector;

    private MaterialFragmentViewModel viewModel;

    private FragmentMaterialBinding binding;

    public MaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MaterialFragmentViewModel.class);
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMaterialBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        MaterialKarutaListAdapter adapter = new MaterialKarutaListAdapter(viewModel.karutaList, adapterInjector);
        binding.recyclerKarutaList.setAdapter(adapter);
        binding.recyclerKarutaList.addItemDecoration(new DividerItemDecoration(inflater.getContext(), DividerItemDecoration.VERTICAL));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.MATERIAL);
        super.onResume();
    }

    @Override
    public void onDetach() {
        analyticsManager = null;
        navigator = null;
        viewModelFactory = null;
        adapterInjector = null;
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        for (ColorFilter colorFilter : ColorFilter.values()) {
            MenuItem menuItem = menu.add(Menu.NONE, colorFilter.ordinal(), Menu.NONE, colorFilter.label(getResources()));
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menuItem.setOnMenuItemClickListener(menuColor -> {
                viewModel.onOptionItemSelected(colorFilter);
                return false;
            });
        }
    }

    @dagger.Module
    public abstract class Module {
        @SuppressWarnings("unused")
        @ForFragment
        @ContributesAndroidInjector(modules = {
                MaterialFragmentViewModelModule.class,
                KarutaListItemViewModelModule.class
        })
        abstract MaterialFragment contributeInjector();
    }
}
