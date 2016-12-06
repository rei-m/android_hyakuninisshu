package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentExamResultBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.ExamResultViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizResultViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.ExamResultFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.ExamResultFragmentModule;

public class ExamResultFragment extends BaseFragment implements ExamResultContact.View {

    public static final String TAG = "ExamResultFragment";

    public static ExamResultFragment newInstance() {
        return new ExamResultFragment();
    }

    @Inject
    ExamResultContact.Actions presenter;

    private FragmentExamResultBinding binding;

    private OnFragmentInteractionListener listener;

    public ExamResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamResultBinding.inflate(inflater, container, false);
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
                .plus(new ExamResultFragmentModule(getContext())).inject(this);
    }

    @Override
    public void initialize(ExamResultViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    @Override
    public void finishExam() {
        if (listener != null) {
            listener.onFinishExam();
        }
    }

    public interface Injector {
        ExamResultFragmentComponent plus(ExamResultFragmentModule fragmentModule);
    }

    public interface OnFragmentInteractionListener {
        void onFinishExam();
    }
}
