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
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.databinding.FragmentExamResultBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamResultFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.ExamResultFragmentViewModelModule;

public class ExamResultFragment extends DaggerFragment {

    public static final String TAG = ExamResultFragment.class.getSimpleName();

    private static final String ARG_EXAM_ID = "examId";

    public static ExamResultFragment newInstance(@NonNull KarutaExamIdentifier karutaExamId) {
        ExamResultFragment fragment = new ExamResultFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EXAM_ID, karutaExamId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    AnalyticsManager analyticsManager;

    @Inject
    Navigator navigator;

    @Inject
    ExamResultFragmentViewModel.Factory viewModelFactory;

    private ExamResultFragmentViewModel viewModel;

    private FragmentExamResultBinding binding;

    private CompositeDisposable disposable;

    private OnFragmentInteractionListener listener;

    public ExamResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExamResultFragmentViewModel.class);
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExamResultBinding.inflate(inflater, container, false);
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
        disposable.addAll(viewModel.onClickBackMenuEvent.subscribe(v -> {
            if (listener != null) {
                listener.onFinishExam();
            }
        }), binding.viewResult.onClickKarutaEvent.subscribe(navigator::navigateToKaruta));
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
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM_RESULT);
        super.onResume();
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
        analyticsManager = null;
        navigator = null;
        viewModelFactory = null;
        listener = null;
        super.onDetach();
    }

    private KarutaExamIdentifier karutaExamIdentifier() throws IllegalArgumentException {
        Bundle args = getArguments();
        if (args != null) {
            return args.getParcelable(ARG_EXAM_ID);
        }
        throw new IllegalArgumentException("argument is missing");
    }

    public interface OnFragmentInteractionListener {
        void onFinishExam();
    }

    @ForFragment
    @dagger.Subcomponent(modules = {ExamResultFragmentViewModelModule.class})
    public interface Subcomponent extends AndroidInjector<ExamResultFragment> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<ExamResultFragment> {

            @SuppressWarnings("UnusedReturnValue")
            public abstract Subcomponent.Builder viewModelModule(ExamResultFragmentViewModelModule module);

            @Override
            public void seedInstance(ExamResultFragment instance) {
                viewModelModule(new ExamResultFragmentViewModelModule(instance.karutaExamIdentifier()));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @SuppressWarnings("unused")
        @Binds
        @IntoMap
        @FragmentKey(ExamResultFragment.class)
        abstract AndroidInjector.Factory<? extends Fragment> bind(Subcomponent.Builder builder);
    }
}
