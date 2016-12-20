package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.ActivityTrainingMasterBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.QuizMasterActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;
import me.rei_m.hyakuninisshu.presentation.karuta.module.QuizMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultFragment;
import me.rei_m.hyakuninisshu.presentation.utilitty.ViewUtil;

public class TrainingMasterActivity extends BaseActivity implements TrainingMasterContact.View,
        HasComponent<QuizMasterActivityComponent>,
        QuizFragment.OnFragmentInteractionListener,
        QuizAnswerFragment.OnFragmentInteractionListener,
        QuizResultFragment.OnFragmentInteractionListener {

    public static Intent createIntent(@NonNull Context context,
                                      @NonNull TrainingRange trainingRange,
                                      @NonNull Kimariji kimariji,
                                      @NonNull KarutaStyle topPhraseStyle,
                                      @NonNull KarutaStyle bottomPhraseStyle) {
        Intent intent = new Intent(context, TrainingMasterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TRAINING_RANGE, trainingRange);
        bundle.putSerializable(ARG_KIMARIJI, kimariji);
        bundle.putSerializable(ARG_TOP_PHRASE_STYLE, topPhraseStyle);
        bundle.putSerializable(ARG_BOTTOM_PHRASE_STYLE, bottomPhraseStyle);
        intent.putExtras(bundle);
        return intent;
    }

    private static final String ARG_TRAINING_RANGE = "trainingRange";

    private static final String ARG_KIMARIJI = "kimarij";

    private static final String ARG_TOP_PHRASE_STYLE = "topPhraseStyle";

    private static final String ARG_BOTTOM_PHRASE_STYLE = "bottomPhraseStyle";

    @Inject
    ActivityNavigator navigator;

    @Inject
    TrainingMasterContact.Actions presenter;

    private QuizMasterActivityComponent component;

    private ActivityTrainingMasterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training_master);

        setSupportActionBar(binding.toolbar);

        TrainingRange trainingRange = (TrainingRange) getIntent().getSerializableExtra(ARG_TRAINING_RANGE);
        Kimariji kimariji = (Kimariji) getIntent().getSerializableExtra(ARG_KIMARIJI);

        if (savedInstanceState == null) {
            presenter.onCreate(this,
                    trainingRange,
                    kimariji);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        component = null;
    }

    @Override
    protected void setupActivityComponent() {
        component = ((App) getApplication()).getComponent().plus(new QuizMasterActivityModule(this));
        component.inject(this);
    }

    @Override
    public QuizMasterActivityComponent getComponent() {
        if (component == null) {
            setupActivityComponent();
        }
        return component;
    }

    @Override
    public void startTraining() {

        KarutaStyle topPhraseStyle = (KarutaStyle) getIntent().getSerializableExtra(ARG_TOP_PHRASE_STYLE);
        KarutaStyle bottomPhraseStyle = (KarutaStyle) getIntent().getSerializableExtra(ARG_BOTTOM_PHRASE_STYLE);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, QuizFragment.newInstance(topPhraseStyle, bottomPhraseStyle), QuizFragment.TAG)
                .commit();
    }

    @Override
    public void showEmpty() {
        binding.textEmpty.setVisibility(View.VISIBLE);
        binding.adView.setVisibility(View.VISIBLE);
        ViewUtil.loadAd(binding.adView);
    }

    @Override
    public void onAnswered(@NonNull String quizId) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.content, QuizAnswerFragment.newInstance(quizId), QuizAnswerFragment.TAG)
                .commit();
    }

    @Override
    public void onClickGoToNext() {

        KarutaStyle topPhraseStyle = (KarutaStyle) getIntent().getSerializableExtra(ARG_TOP_PHRASE_STYLE);
        KarutaStyle bottomPhraseStyle = (KarutaStyle) getIntent().getSerializableExtra(ARG_BOTTOM_PHRASE_STYLE);

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content, QuizFragment.newInstance(topPhraseStyle, bottomPhraseStyle), QuizFragment.TAG)
                .commit();
    }

    @Override
    public void onClickGoToResult() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.content, QuizResultFragment.newInstance(), QuizResultFragment.TAG)
                .commit();

        binding.adView.setVisibility(View.VISIBLE);
        ViewUtil.loadAd(binding.adView);
    }

    @Override
    public void onRestartTraining() {
        startTraining();
        binding.adView.destroy();
        binding.adView.setVisibility(View.GONE);
    }

    @Override
    public void onFinishTraining() {
        finish();
    }
}
