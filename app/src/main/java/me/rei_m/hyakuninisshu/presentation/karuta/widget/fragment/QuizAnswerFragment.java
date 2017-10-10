package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizAnswerBinding;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizAnswerFragmentViewModel;

public class QuizAnswerFragment extends DaggerFragment {

    public static final String TAG = "QuizAnswerFragment";

    private static final String ARG_KARUTA_ID = "karutaId";

    private static final String ARG_EXIST_NEXT_QUIZ = "existNextQuiz";

    private static final long INVALID_KARUTA_ID = -1;

    public static QuizAnswerFragment newInstance(long karutaId,
                                                 boolean existNextQuiz) {
        QuizAnswerFragment fragment = new QuizAnswerFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_KARUTA_ID, karutaId);
        args.putBoolean(ARG_EXIST_NEXT_QUIZ, existNextQuiz);
        fragment.setArguments(args);
        return fragment;
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
        long karutaId = INVALID_KARUTA_ID;
        boolean existNextQuiz = false;

        if (getArguments() != null) {
            karutaId = getArguments().getLong(ARG_KARUTA_ID);
            existNextQuiz = getArguments().getBoolean(ARG_EXIST_NEXT_QUIZ);
        }

        if (karutaId == INVALID_KARUTA_ID) {
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
        super.onDestroyView();
        viewModel = null;
        binding = null;
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
        void onClickGoToNext();

        void onClickGoToResult();

        void onErrorQuiz();

        void onReceiveIllegalArguments();
    }
}
