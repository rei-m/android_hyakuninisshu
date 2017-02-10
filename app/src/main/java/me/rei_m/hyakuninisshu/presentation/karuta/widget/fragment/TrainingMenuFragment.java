package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentTrainingMenuBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.TrainingMenuViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.SpinnerAdapter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.TrainingMenuFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.TrainingMenuFragmentModule;

public class TrainingMenuFragment extends BaseFragment implements TrainingMenuContact.View {

    public static final String TAG = "TrainingMenuFragment";

    public static TrainingMenuFragment newInstance() {
        return new TrainingMenuFragment();
    }

    @Inject
    TrainingMenuContact.Actions presenter;

    @Inject
    ActivityNavigator navigator;

    private FragmentTrainingMenuBinding binding;

    public TrainingMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTrainingMenuBinding.inflate(inflater, container, false);

        Context context = getActivity().getApplicationContext();

        SpinnerAdapter trainingRangeAdapter = SpinnerAdapter.newInstance(context, TrainingRange.values(), false);
        binding.setTrainingRangeAdapter(trainingRangeAdapter);

        SpinnerAdapter kimarijiAdapter = SpinnerAdapter.newInstance(context, Kimariji.values(), false);
        binding.setKimarijiAdapter(kimarijiAdapter);

        SpinnerAdapter karutaStyleAdapter = SpinnerAdapter.newInstance(context, KarutaStyle.values(), false);
        binding.setKarutaStyleAdapter(karutaStyleAdapter);

        SpinnerAdapter colorAdapter = SpinnerAdapter.newInstance(context, Color.values(), false);
        binding.setColorAdapter(colorAdapter);

        binding.setPresenter(presenter);
        binding.setViewModel(new TrainingMenuViewModel(TrainingRange.ALL,
                Kimariji.ALL,
                KarutaStyle.KANJI,
                KarutaStyle.KANA,
                Color.ALL));

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
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new TrainingMenuFragmentModule(getContext())).inject(this);
    }

    @Override
    public void navigateToTraining(TrainingRange trainingRange,
                                   Kimariji kimariji,
                                   Color color,
                                   KarutaStyle topPhraseStyle,
                                   KarutaStyle bottomPhraseStyle) {
        navigator.navigateToTrainingMaster(getActivity(),
                trainingRange,
                kimariji,
                color,
                topPhraseStyle,
                bottomPhraseStyle);
    }

    public interface Injector {
        TrainingMenuFragmentComponent plus(TrainingMenuFragmentModule fragmentModule);
    }
}
