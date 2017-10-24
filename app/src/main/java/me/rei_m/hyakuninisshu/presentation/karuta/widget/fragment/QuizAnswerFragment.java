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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizAnswerBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
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

    @dagger.Module
    public abstract class Module {
        @ForFragment
        @ContributesAndroidInjector(modules = QuizAnswerFragmentViewModelModule.class)
        abstract QuizAnswerFragment contributeInjector();
    }

    @Inject
    QuizAnswerFragmentViewModel viewModel;

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

        viewModel.onCreate(karutaId, existNextQuiz);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentQuizAnswerBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        viewModel = null;
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        disposable = new CompositeDisposable();
        disposable.addAll(viewModel.onClickNextQuizEvent.subscribe(v -> {
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
        void onClickGoToNext();

        void onClickGoToResult();

        void onErrorQuiz();

        void onReceiveIllegalArguments();
    }
}
