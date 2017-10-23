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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import me.rei_m.hyakuninisshu.databinding.FragmentMaterialDetailBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialDetailFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.MaterialDetailFragmentViewModelModule;

public class MaterialDetailFragment extends DaggerFragment {

    public static final String TAG = MaterialDetailFragment.class.getSimpleName();

    private static final String ARG_KARUTA_NO = "karutaNo";

    private static final int INVALID_KARUTA_NO = -1;

    public static MaterialDetailFragment newInstance(int karutaNo) {
        MaterialDetailFragment fragment = new MaterialDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KARUTA_NO, karutaNo);
        fragment.setArguments(args);
        return fragment;
    }

    @dagger.Module
    public abstract class Module {
        @ForFragment
        @ContributesAndroidInjector(modules = MaterialDetailFragmentViewModelModule.class)
        abstract MaterialDetailFragment contributeInjector();
    }

    @Inject
    MaterialDetailFragmentViewModel viewModel;

    private FragmentMaterialDetailBinding binding;

    private OnFragmentInteractionListener listener;

    private int karutaNo = INVALID_KARUTA_NO;

    public MaterialDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            karutaNo = getArguments().getInt(ARG_KARUTA_NO, INVALID_KARUTA_NO);
        }

        if (karutaNo == INVALID_KARUTA_NO) {
            if (listener != null) {
                listener.onReceiveIllegalArguments();
            }
            return;
        }
        viewModel.onCreate(karutaNo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
    public void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        viewModel.onStop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    public void onPause() {
        viewModel.onPause();
        super.onPause();
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
        viewModel = null;
        listener = null;
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onReceiveIllegalArguments();
    }
}
