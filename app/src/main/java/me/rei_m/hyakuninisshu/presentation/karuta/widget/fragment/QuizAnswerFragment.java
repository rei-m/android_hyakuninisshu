package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.databinding.FragmentQuizAnswerBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;

public class QuizAnswerFragment extends BaseFragment implements QuizAnswerContact.View {

    private static final String ARG_QUIZ_ID = "quizId";

    @Inject
    QuizAnswerContact.Actions presenter;

    private FragmentQuizAnswerBinding binding;

    private String quizId;

    private OnFragmentInteractionListener mListener;

    public QuizAnswerFragment() {
        // Required empty public constructor
    }

    public static QuizAnswerFragment newInstance(String quizId) {
        QuizAnswerFragment fragment = new QuizAnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUIZ_ID, quizId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        presenter.onCreate(this);
        if (getArguments() != null) {
            // TODO: エラーチェック.

            quizId = getArguments().getString(ARG_QUIZ_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter.onCreateView(quizId);
        binding = FragmentQuizAnswerBinding.inflate(inflater, container, false);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void initialize(QuizAnswerViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    @Override
    public void goToNext() {
        if (mListener != null) {
            mListener.onClickGoToNext();
        }
    }

    @Override
    public void goToResult() {
        if (mListener != null) {
            mListener.onClickGoToResult();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onClickGoToNext();

        void onClickGoToResult();
    }
}
