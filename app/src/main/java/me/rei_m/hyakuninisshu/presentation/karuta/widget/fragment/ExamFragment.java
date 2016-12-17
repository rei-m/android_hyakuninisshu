package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentExamBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.ExamViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.ExamFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.ExamFragmentModule;

public class ExamFragment extends BaseFragment implements ExamContact.View {

    public static final String TAG = "ExamFragment";

    public static ExamFragment newInstance() {
        return new ExamFragment();
    }

    @Inject
    ExamContact.Actions presenter;

    @Inject
    ActivityNavigator navigator;

    private FragmentExamBinding binding;

    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamBinding.inflate(inflater, container, false);
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
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new ExamFragmentModule(getContext())).inject(this);
    }

    @Override
    public void initialize(@NonNull ExamViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    @Override
    public void navigateToExamMaster() {
        navigator.navigateToExamMaster(getActivity());
    }

    @Override
    public void navigateToTraining() {
        navigator.navigateToExamTrainingMaster(getActivity());
    }

    public interface Injector {
        ExamFragmentComponent plus(ExamFragmentModule fragmentModule);
    }
}
