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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.Binds;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerFragment;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.databinding.FragmentKarutaBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.KarutaFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.KarutaFragmentViewModelModule;

public class KarutaFragment extends DaggerFragment {

    public static final String TAG = KarutaFragment.class.getSimpleName();

    private static final String ARG_KARUTA_ID = "karutaId";

    public static KarutaFragment newInstance(@NonNull KarutaIdentifier karutaId) {
        KarutaFragment fragment = new KarutaFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_KARUTA_ID, karutaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    KarutaFragmentViewModel.Factory viewModelFactory;

    private KarutaFragmentViewModel viewModel;

    private FragmentKarutaBinding binding;

    public KarutaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(KarutaFragmentViewModel.class);
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentKarutaBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        viewModelFactory = null;
        super.onDetach();
    }

    private KarutaIdentifier karutaIdentifier() throws IllegalArgumentException {
        Bundle args = getArguments();
        if (args != null) {
            return args.getParcelable(ARG_KARUTA_ID);
        }
        throw new IllegalArgumentException("argument is missing");
    }

    @ForFragment
    @dagger.Subcomponent(modules = {KarutaFragmentViewModelModule.class})
    public interface Subcomponent extends AndroidInjector<KarutaFragment> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<KarutaFragment> {

            @SuppressWarnings("UnusedReturnValue")
            public abstract Subcomponent.Builder viewModelModule(KarutaFragmentViewModelModule module);

            @Override
            public void seedInstance(KarutaFragment instance) {
                viewModelModule(new KarutaFragmentViewModelModule(instance.karutaIdentifier()));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @SuppressWarnings("unused")
        @Binds
        @IntoMap
        @FragmentKey(KarutaFragment.class)
        abstract AndroidInjector.Factory<? extends Fragment> bind(Subcomponent.Builder builder);
    }
}
