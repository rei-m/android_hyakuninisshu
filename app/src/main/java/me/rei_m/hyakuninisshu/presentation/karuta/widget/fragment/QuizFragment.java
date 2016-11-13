package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.StateVm;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.QuizFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizFragmentModule;

public class QuizFragment extends BaseFragment implements QuizContact.View {

    private static final int SPEED_DISPLAY_ANIMATION_MILL_SEC = 500;

    public static QuizFragment newInstance() {
        return new QuizFragment();
    }

    @Inject
    QuizContact.Actions presenter;

    private FragmentQuizBinding binding;

    private OnFragmentInteractionListener listener;

    private Disposable disposable;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQuizBinding.inflate(inflater, container, false);
        binding.setPresenter(presenter);
        binding.setState(new StateVm());
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
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new QuizFragmentModule(getContext())).inject(this);
    }

    @Override
    public void initialize(QuizViewModel viewModel) {
        binding.setViewModel(viewModel);

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
        final StringBuilder totalKarutaStringBuilder = new StringBuilder();

        totalKarutaStringBuilder.append(viewModel.firstPhrase);
        Observable.fromIterable(firstLine).take(viewModel.firstPhrase.length()).subscribe(totalKarutaTextViewList::add);

        totalKarutaStringBuilder.append(viewModel.secondPhrase);
        Observable.fromIterable(secondLine).take(viewModel.secondPhrase.length()).subscribe(totalKarutaTextViewList::add);

        totalKarutaStringBuilder.append(viewModel.thirdPhrase);
        Observable.fromIterable(thirdLine).take(viewModel.thirdPhrase.length()).subscribe(totalKarutaTextViewList::add);

        Observable.fromIterable(totalKarutaTextViewList).subscribe(textView -> {
            textView.setVisibility(View.INVISIBLE);
        });

        String totalKarutaString = totalKarutaStringBuilder.toString();

        disposable = Observable.interval(SPEED_DISPLAY_ANIMATION_MILL_SEC, TimeUnit.MILLISECONDS).take(totalKarutaString.length())
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
    public void displayAnswer(String quizId, boolean isCollect) {
        if (listener != null) {
//            listener.onAnswered(quizId);
//            binding.getViewModel().setQuizState((isCollect) ? QuizState.ANSWERED_COLLECT : QuizState.ANSWERED_INCORRECT);
//            System.out.println(binding.getViewModel().getQuizState());

        }
    }

    public interface Injector {
        QuizFragmentComponent plus(QuizFragmentModule fragmentModule);
    }

    public interface OnFragmentInteractionListener {
        void onAnswered(String quizId);
    }
}
