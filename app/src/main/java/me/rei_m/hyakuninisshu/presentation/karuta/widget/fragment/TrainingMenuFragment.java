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
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.FragmentTrainingMenuBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.SpinnerAdapter;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.TrainingMenuFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.TrainingMenuFragmentViewModelModule;

public class TrainingMenuFragment extends DaggerFragment {

    public static final String TAG = TrainingMenuFragment.class.getSimpleName();

    public static TrainingMenuFragment newInstance() {
        return new TrainingMenuFragment();
    }

    @dagger.Module
    public abstract class Module {
        @ForFragment
        @ContributesAndroidInjector(modules = TrainingMenuFragmentViewModelModule.class)
        abstract TrainingMenuFragment contributeInjector();
    }

    @Inject
    TrainingMenuFragmentViewModel viewModel;

    private FragmentTrainingMenuBinding binding;

    private CompositeDisposable disposable;

    public TrainingMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTrainingMenuBinding.inflate(inflater, container, false);

        Context context = getActivity().getApplicationContext();

        SpinnerAdapter trainingRangeFromAdapter = SpinnerAdapter.newInstance(context, TrainingRangeFrom.values(), false);
        binding.setTrainingRangeFromAdapter(trainingRangeFromAdapter);

        SpinnerAdapter trainingRangeToAdapter = SpinnerAdapter.newInstance(context, TrainingRangeTo.values(), false);
        binding.setTrainingRangeToAdapter(trainingRangeToAdapter);

        SpinnerAdapter kimarijiAdapter = SpinnerAdapter.newInstance(context, KimarijiFilter.values(), false);
        binding.setKimarijiAdapter(kimarijiAdapter);

        SpinnerAdapter karutaStyleAdapter = SpinnerAdapter.newInstance(context, KarutaStyleFilter.values(), false);
        binding.setKarutaStyleAdapter(karutaStyleAdapter);

        SpinnerAdapter colorAdapter = SpinnerAdapter.newInstance(context, ColorFilter.values(), false);
        binding.setColorAdapter(colorAdapter);

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
        disposable = new CompositeDisposable();
        disposable.addAll(viewModel.invalidTrainingRangeEvent.subscribe(v -> {
            Snackbar.make(binding.getRoot(), R.string.text_message_invalid_training_range, Snackbar.LENGTH_SHORT).show();
        }));
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
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
