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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.databinding.FragmentExamBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.ExamFragmentViewModelModule;

public class ExamFragment extends DaggerFragment {

    public static final String TAG = ExamFragment.class.getSimpleName();

    public static ExamFragment newInstance() {
        return new ExamFragment();
    }

    @Inject
    AnalyticsManager analyticsManager;

    @Inject
    Navigator navigator;

    @Inject
    ExamFragmentViewModel.Factory viewModelFactory;

    private ExamFragmentViewModel viewModel;

    private FragmentExamBinding binding;

    private CompositeDisposable disposable;

    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExamFragmentViewModel.class);
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamBinding.inflate(inflater, container, false);
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
        disposable = new CompositeDisposable();
        disposable.addAll(viewModel.onClickStartExamEvent.subscribe(v -> {
            analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_EXAM);
            navigator.navigateToExamMaster();
        }), viewModel.onClickStartTrainingEvent.subscribe(v -> {
            analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING_FOR_EXAM);
            navigator.navigateToExamTrainingMaster();
        }));
    }

    @Override
    public void onStop() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM);
        super.onResume();
    }

    @Override
    public void onDetach() {
        navigator = null;
        analyticsManager = null;
        viewModelFactory = null;
        super.onDetach();
    }

    @dagger.Module
    public abstract class Module {
        @SuppressWarnings("unused")
        @ForFragment
        @ContributesAndroidInjector(modules = ExamFragmentViewModelModule.class)
        abstract ExamFragment contributeInjector();
    }
}
