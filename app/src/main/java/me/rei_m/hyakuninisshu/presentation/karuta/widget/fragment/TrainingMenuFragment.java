package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentTrainingMenuBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
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

        SpinnerAdapter trainingRangeFromAdapter = SpinnerAdapter.newInstance(context, TrainingRangeFrom.values(), false);
        binding.setTrainingRangeFromAdapter(trainingRangeFromAdapter);

        SpinnerAdapter trainingRangeToAdapter = SpinnerAdapter.newInstance(context, TrainingRangeTo.values(), false);
        binding.setTrainingRangeToAdapter(trainingRangeToAdapter);

        SpinnerAdapter kimarijiAdapter = SpinnerAdapter.newInstance(context, Kimariji.values(), false);
        binding.setKimarijiAdapter(kimarijiAdapter);

        SpinnerAdapter karutaStyleAdapter = SpinnerAdapter.newInstance(context, KarutaStyle.values(), false);
        binding.setKarutaStyleAdapter(karutaStyleAdapter);

        SpinnerAdapter colorAdapter = SpinnerAdapter.newInstance(context, Color.values(), false);
        binding.setColorAdapter(colorAdapter);

        binding.setPresenter(presenter);
        binding.setViewModel(new TrainingMenuViewModel(TrainingRangeFrom.ONE,
                TrainingRangeTo.ONE_HUNDRED,
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
    public void showInvalidTrainingRangeMessage() {
        Snackbar.make(binding.getRoot(), R.string.text_message_invalid_training_range, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToTraining(@NonNull TrainingRangeFrom trainingRangeFrom,
                                   @NonNull TrainingRangeTo trainingRangeTo,
                                   @NonNull Kimariji kimariji,
                                   @NonNull Color color,
                                   @NonNull KarutaStyle topPhraseStyle,
                                   @NonNull KarutaStyle bottomPhraseStyle) {
        navigator.navigateToTrainingMaster(getActivity(),
                trainingRangeFrom,
                trainingRangeTo,
                kimariji,
                color,
                topPhraseStyle,
                bottomPhraseStyle);
    }

    public interface Injector {
        TrainingMenuFragmentComponent plus(TrainingMenuFragmentModule fragmentModule);
    }
}
