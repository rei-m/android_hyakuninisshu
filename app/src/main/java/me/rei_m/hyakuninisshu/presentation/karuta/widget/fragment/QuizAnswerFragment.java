package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizAnswerBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizAnswerViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.QuizAnswerFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizAnswerFragmentModule;

public class QuizAnswerFragment extends BaseFragment implements QuizAnswerContact.View {

    public static final String TAG = "QuizAnswerFragment";

    private static final String ARG_QUIZ_ID = "quizId";

    public static QuizAnswerFragment newInstance(String quizId) {
        QuizAnswerFragment fragment = new QuizAnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUIZ_ID, quizId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    QuizAnswerContact.Actions presenter;

    private FragmentQuizAnswerBinding binding;

    private String quizId;

    private OnFragmentInteractionListener listener;

    public QuizAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: エラーチェック.

            quizId = getArguments().getString(ARG_QUIZ_ID);
        }
        presenter.onCreate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
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
    public void onResume() {
        super.onResume();
        presenter.onResume(quizId);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
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
                .plus(new QuizAnswerFragmentModule(getContext())).inject(this);
    }

    @Override
    public void initialize(QuizAnswerViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    @Override
    public void goToNext() {
        if (listener != null) {
            listener.onClickGoToNext();
        }
    }

    @Override
    public void goToResult() {
        if (listener != null) {
            listener.onClickGoToResult();
        }
    }

    public interface Injector {
        QuizAnswerFragmentComponent plus(QuizAnswerFragmentModule fragmentModule);
    }

    public interface OnFragmentInteractionListener {
        void onClickGoToNext();

        void onClickGoToResult();
    }
}
