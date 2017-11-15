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
import me.rei_m.hyakuninisshu.databinding.FragmentQuizAnswerBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizAnswerFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.QuizAnswerFragmentViewModelModule;

public class QuizAnswerFragment extends DaggerFragment {

    public static final String TAG = QuizAnswerFragment.class.getSimpleName();

    private static final String ARG_KARUTA_ID = "karutaId";

    private static final String ARG_EXIST_NEXT_QUIZ = "existNextQuiz";

    public static QuizAnswerFragment newInstance(@NonNull KarutaIdentifier karutaId,
                                                 boolean existNextQuiz) {
        QuizAnswerFragment fragment = new QuizAnswerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_KARUTA_ID, karutaId);
        args.putBoolean(ARG_EXIST_NEXT_QUIZ, existNextQuiz);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    Navigator navigator;

    @Inject
    QuizAnswerFragmentViewModel.Factory viewModelFactory;

    private QuizAnswerFragmentViewModel viewModel;

    private FragmentQuizAnswerBinding binding;

    private OnFragmentInteractionListener listener;

    private CompositeDisposable disposable;

    public QuizAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KarutaIdentifier karutaId = null;
        boolean existNextQuiz = false;

        if (getArguments() != null) {
            karutaId = getArguments().getParcelable(ARG_KARUTA_ID);
            existNextQuiz = getArguments().getBoolean(ARG_EXIST_NEXT_QUIZ);
        }

        if (karutaId == null) {
            if (listener != null) {
                listener.onReceiveIllegalArguments();
            }
            return;
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizAnswerFragmentViewModel.class);
    }

    @Override
    public void onDestroy() {
        viewModel = null;
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuizAnswerBinding.inflate(inflater, container, false);
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
        disposable.addAll(viewModel.onClickAnswerEvent.subscribe(karutaIdentifier -> {
            navigator.navigateToMaterialSingle(karutaIdentifier);
        }), viewModel.onClickNextQuizEvent.subscribe(v -> {
            if (listener != null) {
                listener.onClickGoToNext();
            }
        }), viewModel.onClickConfirmResultEvent.subscribe(v -> {
            if (listener != null) {
                listener.onClickGoToResult();
            }
        }), viewModel.errorEvent.subscribe(v -> {
            if (listener != null) {
                listener.onErrorQuiz();
            }
        }));
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
        navigator = null;
        viewModelFactory = null;
        listener = null;
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onClickGoToNext();

        void onClickGoToResult();

        void onErrorQuiz();

        void onReceiveIllegalArguments();
    }

    @ForFragment
    @dagger.Subcomponent(modules = {QuizAnswerFragmentViewModelModule.class})
    public interface Subcomponent extends AndroidInjector<QuizAnswerFragment> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<QuizAnswerFragment> {

            @SuppressWarnings("UnusedReturnValue")
            public abstract Subcomponent.Builder fragmentModule(QuizAnswerFragmentViewModelModule module);

            @Override
            public void seedInstance(QuizAnswerFragment instance) {
                Bundle args = instance.getArguments();
                KarutaIdentifier karutaId = args.getParcelable(ARG_KARUTA_ID);
                boolean existNextQuiz = args.getBoolean(ARG_EXIST_NEXT_QUIZ);
                fragmentModule(new QuizAnswerFragmentViewModelModule(karutaId, existNextQuiz));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @SuppressWarnings("unused")
        @Binds
        @IntoMap
        @FragmentKey(QuizAnswerFragment.class)
        abstract AndroidInjector.Factory<? extends Fragment> bind(Subcomponent.Builder builder);
    }
}
