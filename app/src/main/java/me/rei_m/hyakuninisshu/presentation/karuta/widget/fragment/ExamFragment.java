package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentExamBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.ExamFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.ExamFragmentModule;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamFragmentViewModel;

public class ExamFragment extends BaseFragment {

    public static final String TAG = "ExamFragment";

    public static ExamFragment newInstance() {
        return new ExamFragment();
    }

    @Inject
    ExamFragmentViewModel viewModel;

    private FragmentExamBinding binding;

    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamBinding.inflate(inflater, container, false);
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
        viewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new ExamFragmentModule(getContext())).inject(this);
    }

    public interface Injector {
        ExamFragmentComponent plus(ExamFragmentModule fragmentModule);
    }
}
