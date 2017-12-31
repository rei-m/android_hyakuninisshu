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
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.FragmentTrainingMenuBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeTo;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.SpinnerAdapter;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.TrainingMenuFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.TrainingMenuFragmentViewModelModule;

public class TrainingMenuFragment extends DaggerFragment {

    public static final String TAG = TrainingMenuFragment.class.getSimpleName();

    private static final String KEY_TRAINING_RANGE_FROM = "trainingRangeFrom";

    private static final String KEY_TRAINING_RANGE_TO = "trainingRangeTo";

    private static final String KEY_KIMARIJI = "kimarij";

    private static final String KEY_COLOR = "color";

    private static final String KEY_KAMI_NO_KU_STYLE = "kamiNoKuStyle";

    private static final String KEY_SHIMO_NO_KU_STYLE = "shimoNoKuStyle";


    public static TrainingMenuFragment newInstance() {
        return new TrainingMenuFragment();
    }

    @Inject
    Context context;

    @Inject
    AnalyticsManager analyticsManager;

    @Inject
    Navigator navigator;

    @Inject
    TrainingMenuFragmentViewModel.Factory viewModelFactory;

    private TrainingMenuFragmentViewModel viewModel;

    private FragmentTrainingMenuBinding binding;

    private CompositeDisposable disposable;

    public TrainingMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            viewModelFactory.setTrainingRangeFrom(TrainingRangeFrom.get(savedInstanceState.getInt(KEY_TRAINING_RANGE_FROM)));
            viewModelFactory.setTrainingRangeTo(TrainingRangeTo.get(savedInstanceState.getInt(KEY_TRAINING_RANGE_TO)));
            viewModelFactory.setKimariji(KimarijiFilter.get(savedInstanceState.getInt(KEY_KIMARIJI)));
            viewModelFactory.setKamiNoKuStyle(KarutaStyleFilter.get(savedInstanceState.getInt(KEY_KAMI_NO_KU_STYLE)));
            viewModelFactory.setShimoNoKuStyle(KarutaStyleFilter.get(savedInstanceState.getInt(KEY_SHIMO_NO_KU_STYLE)));
            viewModelFactory.setColor(ColorFilter.get(savedInstanceState.getInt(KEY_COLOR)));
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrainingMenuFragmentViewModel.class);
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentTrainingMenuBinding.inflate(inflater, container, false);

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
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        disposable = new CompositeDisposable();
        disposable.addAll(viewModel.onClickStartTrainingEvent.subscribe(v -> {
            analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING);
            navigator.navigateToTrainingMaster(viewModel.trainingRangeFrom.get(),
                    viewModel.trainingRangeTo.get(),
                    viewModel.kimariji.get(),
                    viewModel.color.get(),
                    viewModel.kamiNoKuStyle.get(),
                    viewModel.shimoNoKuStyle.get());
        }), viewModel.invalidTrainingRangeEvent.subscribe(v ->
                Snackbar.make(binding.getRoot(), R.string.text_message_invalid_training_range, Snackbar.LENGTH_SHORT).show()
        ));
    }

    @Override
    public void onStop() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.TRAINING_MENU);
        super.onResume();
    }

    @Override
    public void onDetach() {
        context = null;
        analyticsManager = null;
        navigator = null;
        viewModelFactory = null;
        super.onDetach();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TRAINING_RANGE_FROM, viewModel.trainingRangeFrom.get().ordinal());
        outState.putInt(KEY_TRAINING_RANGE_TO, viewModel.trainingRangeTo.get().ordinal());
        outState.putInt(KEY_KIMARIJI, viewModel.kimariji.get().ordinal());
        outState.putInt(KEY_KAMI_NO_KU_STYLE, viewModel.kamiNoKuStyle.get().ordinal());
        outState.putInt(KEY_SHIMO_NO_KU_STYLE, viewModel.shimoNoKuStyle.get().ordinal());
        outState.putInt(KEY_COLOR, viewModel.color.get().ordinal());
    }

    @dagger.Module
    public abstract class Module {
        @SuppressWarnings("unused")
        @ForFragment
        @ContributesAndroidInjector(modules = TrainingMenuFragmentViewModelModule.class)
        abstract TrainingMenuFragment contributeInjector();
    }
}
