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
import android.content.Context;
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
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialDetailBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialDetailFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.MaterialDetailFragmentViewModelModule;

public class MaterialDetailFragment extends DaggerFragment {

    public static final String TAG = MaterialDetailFragment.class.getSimpleName();

    private static final String ARG_KARUTA_NO = "karutaNo";

    public static MaterialDetailFragment newInstance(@NonNull KarutaIdentifier karutaId) {
        MaterialDetailFragment fragment = new MaterialDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_KARUTA_NO, karutaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MaterialDetailFragmentViewModel.Factory viewModelFactory;

    private MaterialDetailFragmentViewModel viewModel;

    private FragmentMaterialDetailBinding binding;

    private OnFragmentInteractionListener listener;

    private KarutaIdentifier karutaId;

    public MaterialDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            karutaId = getArguments().getParcelable(ARG_KARUTA_NO);
        }

        if (karutaId == null) {
            if (listener != null) {
                listener.onReceiveIllegalArguments();
            }
            return;
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MaterialDetailFragmentViewModel.class);
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMaterialDetailBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onReceiveIllegalArguments();
    }

    @ForFragment
    @dagger.Subcomponent(modules = {MaterialDetailFragmentViewModelModule.class})
    public interface Subcomponent extends AndroidInjector<MaterialDetailFragment> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<MaterialDetailFragment> {

            @SuppressWarnings("UnusedReturnValue")
            public abstract Subcomponent.Builder viewModelModule(MaterialDetailFragmentViewModelModule module);

            @Override
            public void seedInstance(MaterialDetailFragment instance) {
                Bundle args = instance.getArguments();
                KarutaIdentifier karutaId = args.getParcelable(ARG_KARUTA_NO);
                viewModelModule(new MaterialDetailFragmentViewModelModule(karutaId));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @SuppressWarnings("unused")
        @Binds
        @IntoMap
        @FragmentKey(MaterialDetailFragment.class)
        abstract AndroidInjector.Factory<? extends Fragment> bind(Subcomponent.Builder builder);
    }
}
