package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizResultBinding;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizResultFragmentViewModel;

public class QuizResultFragment extends DaggerFragment {

    public static final String TAG = "QuizResultFragment";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizResultBinding.inflate(inflater, container, false);
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
        viewModel = null;
        listener = null;
    }

    public interface OnFragmentInteractionListener {

        void onRestartTraining();

        void onFinishTraining();

        void onErrorQuiz();
    }
}
