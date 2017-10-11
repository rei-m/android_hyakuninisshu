package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.databinding.FragmentExamResultBinding;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamResultFragmentViewModel;

public class ExamResultFragment extends DaggerFragment {

    public static final String TAG = "ExamResultFragment";

    private static final String ARG_EXAM_ID = "examId";

    private static final long INVALID_EXAM_ID = -1;

    public static ExamResultFragment newInstance(@NonNull Long examId) {
        ExamResultFragment fragment = new ExamResultFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_EXAM_ID, examId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    ExamResultFragmentViewModel viewModel;

    private FragmentExamResultBinding binding;

    private OnFragmentInteractionListener listener;

    private CompositeDisposable disposable;

    public ExamResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long examId = INVALID_EXAM_ID;
        if (getArguments() != null) {
            examId = getArguments().getLong(ARG_EXAM_ID, INVALID_EXAM_ID);
        }

        if (examId == INVALID_EXAM_ID) {
            if (listener != null) {
                listener.onReceiveIllegalArguments();
            }
            return;
        }

        viewModel.onCreate(examId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExamResultBinding.inflate(inflater, container, false);
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
        disposable.addAll(viewModel.onClickBackMenuEvent.subscribe(v -> {
            if (listener != null) {
                listener.onFinishExam();
            }
        }), binding.viewResult.onClickKarutaEvent.subscribe(karutaNo -> viewModel.onClickResult(karutaNo)));
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
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
        void onFinishExam();

        void onReceiveIllegalArguments();
    }
}
