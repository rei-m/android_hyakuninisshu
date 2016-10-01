package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.databinding.FragmentTrainingMenuBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;

public class TrainingMenuFragment extends BaseFragment implements TrainingMenuContact.View {

    public static final String TAG = "TrainingMenuFragment";

    @Inject
    TrainingMenuContact.Actions presenter;

    public TrainingMenuFragment() {
        // Required empty public constructor
    }

    public static TrainingMenuFragment newInstance() {
        return new TrainingMenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        presenter.onCreate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTrainingMenuBinding binding = FragmentTrainingMenuBinding.inflate(inflater, container, false);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void navigateToTraining() {

    }
}
