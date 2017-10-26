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
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerFragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizBinding;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.presentation.helper.Device;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizFragmentViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di.QuizFragmentViewModelModule;

public class QuizFragment extends DaggerFragment {

    public static final String TAG = QuizFragment.class.getSimpleName();

    private static final String ARG_KAMI_NO_KU_STYLE = "kamiNoKuStyle";

    private static final String ARG_SHIMO_NO_KU_STYLE = "shimoNoKuStyle";

    private static final int SPEED_DISPLAY_ANIMATION_MILL_SEC = 500;

    private static final String KEY_QUIZ_ID = "quizId";

    public static QuizFragment newInstance(@NonNull KarutaStyleFilter kamiNoKuStyle,
                                           @NonNull KarutaStyleFilter shimoNoKuStyle) {

        QuizFragment fragment = new QuizFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_KAMI_NO_KU_STYLE, kamiNoKuStyle.ordinal());
        args.putInt(ARG_SHIMO_NO_KU_STYLE, shimoNoKuStyle.ordinal());
        fragment.setArguments(args);

        return fragment;
    }

    @Inject
    QuizFragmentViewModel viewModel;

    @Inject
    Device device;

    private FragmentQuizBinding binding;

    private OnFragmentInteractionListener listener;

    private CompositeDisposable disposable;

    private Disposable animationDisposable;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        KarutaStyleFilter kamiNoKuStyle = KarutaStyleFilter.get(args.getInt(ARG_KAMI_NO_KU_STYLE));
        KarutaStyleFilter shimoNoKuStyle = KarutaStyleFilter.get(args.getInt(ARG_SHIMO_NO_KU_STYLE));

        if (savedInstanceState != null) {
            KarutaQuizIdentifier quizId = savedInstanceState.getParcelable(KEY_QUIZ_ID);
            if (quizId != null) {
                viewModel.onReCreate(quizId, kamiNoKuStyle, shimoNoKuStyle);
                return;
            }
        }
        viewModel.onCreate(kamiNoKuStyle, shimoNoKuStyle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);

        Point windowSize = device.getWindowSize();
        RelativeViewSize relativeViewSize = new RelativeViewSize(windowSize);

        binding.setViewModel(viewModel);
        binding.setViewSize(relativeViewSize);

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

        disposable.addAll(viewModel.startDisplayAnimationEvent.subscribe(v ->
                startDisplayQuizAnimation(
                        viewModel.firstPhrase.get(),
                        viewModel.secondPhrase.get(),
                        viewModel.thirdPhrase.get()
                )
        ), viewModel.stopDisplayAnimationEvent.subscribe(v -> {
            if (animationDisposable != null) {
                animationDisposable.dispose();
                animationDisposable = null;
            }
        }), viewModel.onClickResultEvent.subscribe(v -> {
            if (listener != null) {
                listener.onAnswered(viewModel.correctKarutaId(), viewModel.existNextQuiz());
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
        if (animationDisposable != null) {
            animationDisposable.dispose();
            animationDisposable = null;
        }
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
        device = null;
        listener = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_QUIZ_ID, viewModel.karutaQuizId());
    }

    // TODO: DataBindingに寄せることができるはず.
    private void startDisplayQuizAnimation(@NonNull String firstPhrase,
                                           @NonNull String secondPhrase,
                                           @NonNull String thirdPhrase) {

        if (animationDisposable != null) {
            return;
        }

        final List<TextView> firstLine = new ArrayList<>(Arrays.asList(
                binding.phrase11,
                binding.phrase12,
                binding.phrase13,
                binding.phrase14,
                binding.phrase15,
                binding.phrase16
        ));

        final List<TextView> secondLine = new ArrayList<>(Arrays.asList(
                binding.phrase21,
                binding.phrase22,
                binding.phrase23,
                binding.phrase24,
                binding.phrase25,
                binding.phrase26,
                binding.phrase27,
                binding.phrase28
        ));

        final List<TextView> thirdLine = new ArrayList<>(Arrays.asList(
                binding.phrase31,
                binding.phrase32,
                binding.phrase33,
                binding.phrase34,
                binding.phrase35,
                binding.phrase36
        ));

        final List<TextView> totalKarutaTextViewList = new ArrayList<>();
        Observable.fromIterable(firstLine).take(firstPhrase.length()).subscribe(totalKarutaTextViewList::add);
        Observable.fromIterable(secondLine).take(secondPhrase.length()).subscribe(totalKarutaTextViewList::add);
        Observable.fromIterable(thirdLine).take(thirdPhrase.length()).subscribe(totalKarutaTextViewList::add);

        Observable.fromIterable(totalKarutaTextViewList).subscribe(textView -> textView.setVisibility(View.INVISIBLE));

        animationDisposable = Observable.interval(SPEED_DISPLAY_ANIMATION_MILL_SEC, TimeUnit.MILLISECONDS)
                .zipWith(totalKarutaTextViewList, (aLong, textView) -> textView)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textView -> {
                    final Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setInterpolator(new DecelerateInterpolator());
                    fadeIn.setDuration(SPEED_DISPLAY_ANIMATION_MILL_SEC);
                    textView.setVisibility(View.VISIBLE);
                    textView.startAnimation(fadeIn);
                });
    }

    public interface OnFragmentInteractionListener {
        void onAnswered(@NonNull KarutaIdentifier karutaId, boolean existNextQuiz);

        void onErrorQuiz();
    }

    public static class RelativeViewSize {

        public final int quizTextSize;
        public final int choiceTextSize;

        RelativeViewSize(@NonNull Point windowSize) {
            int windowWidth = windowSize.x;
            int windowHeight = windowSize.y;

            int phraseHeight = windowHeight / 2;
            quizTextSize = phraseHeight / 13;

            int choiceWidth = windowWidth / 4;
            choiceTextSize = choiceWidth / 5;
        }
    }

    @dagger.Module
    public abstract class Module {
        @SuppressWarnings("unused")
        @ForFragment
        @ContributesAndroidInjector(modules = QuizFragmentViewModelModule.class)
        abstract QuizFragment contributeInjector();
    }
}
