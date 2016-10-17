package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.databinding.FragmentTrainingMenuBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.component.adapter.SpinnerAdapter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;

public class TrainingMenuFragment extends BaseFragment implements TrainingMenuContact.View {

    public static final String TAG = "TrainingMenuFragment";

    @Inject
    TrainingMenuContact.Actions presenter;

    @Inject
    ActivityNavigator navigator;

    private FragmentTrainingMenuBinding binding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTrainingMenuBinding.inflate(inflater, container, false);

        Context context = getActivity().getApplicationContext();

        SpinnerAdapter trainingRangeAdapter = SpinnerAdapter.newInstance(context, TrainingRange.values(), false);
        binding.spinnerRangeQuestion.setAdapter(trainingRangeAdapter);

        SpinnerAdapter kimarijiAdapter = SpinnerAdapter.newInstance(context, Kimariji.values(), false);
        binding.spinnerKimariji.setAdapter(kimarijiAdapter);

        binding.setPresenter(presenter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter = null;
        binding = null;
    }

    @Override
    public void navigateToTraining() {
        navigator.navigateToQuizMaster(getActivity());
    }
}
