package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import me.rei_m.hyakuninisshu.databinding.FragmentExamBinding;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamFragmentViewModel;

public class ExamFragment extends Fragment {

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
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewModel = null;
    }
}
