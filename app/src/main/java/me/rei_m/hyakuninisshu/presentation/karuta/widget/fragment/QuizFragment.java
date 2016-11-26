package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.QuizState;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.QuizFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizFragmentModule;
import me.rei_m.hyakuninisshu.presentation.manager.DeviceManager;

public class QuizFragment extends BaseFragment implements QuizContact.View {

    private static final int SPEED_DISPLAY_ANIMATION_MILL_SEC = 500;

    private static final String KEY_VIEW_MODEL = "viewModel";

    private static final String KEY_QUIZ_STATE = "quizState";

    public static QuizFragment newInstance() {
        return new QuizFragment();
    }

    @Inject
    QuizContact.Actions presenter;

    @Inject
    DeviceManager deviceManager;

    private FragmentQuizBinding binding;

    private OnFragmentInteractionListener listener;

    private Disposable disposable;

    private QuizViewModel viewModel;

    private QuizState state = QuizState.UNANSWERED;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            viewModel = (QuizViewModel) savedInstanceState.getSerializable(KEY_VIEW_MODEL);
            state = (QuizState) savedInstanceState.getSerializable(KEY_QUIZ_STATE);
        }
        presenter.onCreate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);

        Point windowSize = deviceManager.getWindowSize();
        RelativeViewSize relativeViewSize = new RelativeViewSize(windowSize);

        binding.setPresenter(presenter);
        binding.setViewSize(relativeViewSize);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(viewModel, state);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
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
        super.onDetach();
        listener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_VIEW_MODEL, viewModel);
        outState.putSerializable(KEY_QUIZ_STATE, state);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new QuizFragmentModule(getContext())).inject(this);
    }

    @Override
    public void initialize(@NonNull QuizViewModel viewModel) {

        this.viewModel = viewModel;

        binding.setViewModel(viewModel);
    }

    @Override
    public void startDisplayQuizAnimation(@NonNull QuizViewModel viewModel) {
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
        Observable.fromIterable(firstLine).take(viewModel.firstPhrase.length()).subscribe(totalKarutaTextViewList::add);
        Observable.fromIterable(secondLine).take(viewModel.secondPhrase.length()).subscribe(totalKarutaTextViewList::add);
        Observable.fromIterable(thirdLine).take(viewModel.thirdPhrase.length()).subscribe(totalKarutaTextViewList::add);

        Observable.fromIterable(totalKarutaTextViewList).subscribe(textView -> {
            textView.setVisibility(View.INVISIBLE);
        });

        disposable = Observable.interval(SPEED_DISPLAY_ANIMATION_MILL_SEC, TimeUnit.MILLISECONDS)
                .zipWith(totalKarutaTextViewList, (aLong, textView) -> textView)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textView -> {
                    final Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setInterpolator(new DecelerateInterpolator());
                    fadeIn.setDuration(SPEED_DISPLAY_ANIMATION_MILL_SEC);
                    textView.setVisibility(View.VISIBLE);
                    textView.startAnimation(fadeIn);
                });
    }

    @Override
    public void displayResult(@NonNull String quizId, boolean isCollect) {
        if (isCollect) {
            state = QuizState.ANSWERED_COLLECT;
            binding.imageQuizResult.setImageResource(R.drawable.check_correct);
        } else {
            state = QuizState.ANSWERED_INCORRECT;
            binding.imageQuizResult.setImageResource(R.drawable.check_incorrect);
        }
        binding.layoutQuizResult.setVisibility(View.VISIBLE);

        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    public void displayAnswer(@NonNull String quizId) {
        if (listener != null) {
            listener.onAnswered(quizId);
        }
    }

    public interface Injector {
        QuizFragmentComponent plus(QuizFragmentModule fragmentModule);
    }

    public interface OnFragmentInteractionListener {
        void onAnswered(String quizId);
    }

    public static class RelativeViewSize {

        public final int windowWidth;
        public final int windowHeight;
        public final int phraseHeight;
        public final int choiceWidth;
        public final int quizTextSize;
        public final int choiceTextSize;

        public RelativeViewSize(@NonNull Point windowSize) {
            windowWidth = windowSize.x;
            windowHeight = windowSize.y;

            phraseHeight = windowHeight / 2;
            quizTextSize = phraseHeight / 13;

            int hoge = windowWidth / 4;
            choiceTextSize = hoge / 5;
            choiceWidth = choiceTextSize * 3;
        }
    }
}