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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import me.rei_m.hyakuninisshu.databinding.FragmentExamBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.ExamFragmentViewModelModule;

public class ExamFragment extends DaggerFragment {

    public static final String TAG = ExamFragment.class.getSimpleName();

    public static ExamFragment newInstance() {
        return new ExamFragment();
    }

    @dagger.Module
    public abstract class Module {
        @ForFragment
        @ContributesAndroidInjector(modules = ExamFragmentViewModelModule.class)
        abstract ExamFragment contributeInjector();
    }

    @Inject
    ExamFragmentViewModel viewModel;

    private FragmentExamBinding binding;

    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
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
    public void onDetach() {
        super.onDetach();
        viewModel = null;
    }
}
