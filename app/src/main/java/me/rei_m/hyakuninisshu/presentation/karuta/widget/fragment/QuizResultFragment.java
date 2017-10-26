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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizResultBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizResultFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.QuizResultFragmentViewModelModule;

public class QuizResultFragment extends DaggerFragment {

    public static final String TAG = QuizResultFragment.class.getSimpleName();

    public static QuizResultFragment newInstance() {
        return new QuizResultFragment();
    }

    @Inject
    QuizResultFragmentViewModel viewModel;

    private FragmentQuizResultBinding binding;

    private OnFragmentInteractionListener listener;

    private CompositeDisposable disposable;

    public QuizResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuizResultBinding.inflate(inflater, container, false);
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
        disposable.addAll(viewModel.restartEvent.subscribe(v -> {
            if (listener != null) {
                listener.onRestartTraining();
            }
        }), viewModel.onClickBackMenuEvent.subscribe(v -> {
            if (listener != null) {
                listener.onFinishTraining();
            }
        }), viewModel.errorEvent.subscribe(v -> {
            if (listener != null) {
                listener.onErrorQuiz();
            }
        }));
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
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

        void onRestartTraining();

        void onFinishTraining();

        void onErrorQuiz();
    }

    @dagger.Module
    public abstract class Module {
        @SuppressWarnings("unused")
        @ForFragment
        @ContributesAndroidInjector(modules = QuizResultFragmentViewModelModule.class)
        abstract QuizResultFragment contributeInjector();
    }
}
