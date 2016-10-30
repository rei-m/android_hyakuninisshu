package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.os.Bundle;
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

import me.rei_m.hyakuninisshu.databinding.FragmentQuizBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QuizFragment extends BaseFragment implements QuizContact.View {

    private static final int SPEED_DISPLAY_ANIMATION_MILL_SEC = 500;

    @Inject
    QuizContact.Actions presenter;

    private FragmentQuizBinding binding;

    private OnFragmentInteractionListener listener;

    private Subscription subscription;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance() {
        return new QuizFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        presenter.onCreate(this);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQuizBinding.inflate(inflater, container, false);
        binding.setPresenter(presenter);
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
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
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
    public void initialize(QuizViewModel viewModel) {
        binding.setViewModel(viewModel);

        final List<TextView> firstLine = new ArrayList<>(Arrays.asList(
                binding.phrase11,
                binding.phrase12,
                binding.phrase13,
                binding.phrase14,
                binding.phrase15
        ));

        final List<TextView> secondLine = new ArrayList<>(Arrays.asList(
                binding.phrase21,
                binding.phrase22,
                binding.phrase23,
                binding.phrase24,
                binding.phrase25,
                binding.phrase26,
                binding.phrase27
        ));

        final List<TextView> thirdLine = new ArrayList<>(Arrays.asList(
                binding.phrase31,
                binding.phrase32,
                binding.phrase33,
                binding.phrase34,
                binding.phrase35
        ));

        final List<TextView> totalKarutaTextViewList = new ArrayList<>();
        final StringBuilder totalKarutaStringBuilder = new StringBuilder();

        totalKarutaStringBuilder.append(viewModel.firstPhrase);
        Observable.from(firstLine).take(viewModel.firstPhrase.length()).subscribe(totalKarutaTextViewList::add);

        totalKarutaStringBuilder.append(viewModel.secondPhrase);
        Observable.from(secondLine).take(viewModel.secondPhrase.length()).subscribe(totalKarutaTextViewList::add);

        totalKarutaStringBuilder.append(viewModel.thirdPhrase);
        Observable.from(thirdLine).take(viewModel.thirdPhrase.length()).subscribe(totalKarutaTextViewList::add);

        Observable.from(totalKarutaTextViewList).subscribe(textView -> {
            textView.setVisibility(View.INVISIBLE);
        });

        String totalKarutaString = totalKarutaStringBuilder.toString();

        subscription = Observable.interval(SPEED_DISPLAY_ANIMATION_MILL_SEC, TimeUnit.MILLISECONDS).take(totalKarutaString.length())
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(indexL -> {
                    final int index = indexL.intValue();
                    final Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setInterpolator(new DecelerateInterpolator());
                    fadeIn.setDuration(SPEED_DISPLAY_ANIMATION_MILL_SEC);

                    final TextView currentPhraseView = totalKarutaTextViewList.get(index);
                    currentPhraseView.setText(totalKarutaString.substring(index, index + 1));
                    currentPhraseView.setVisibility(View.VISIBLE);
                    currentPhraseView.startAnimation(fadeIn);
                });
    }

    @Override
    public void displayAnswer(String quizId) {
        if (listener != null) {
            listener.onAnswered(quizId);
        }
    }

    public interface OnFragmentInteractionListener {
        void onAnswered(String quizId);
    }
}
