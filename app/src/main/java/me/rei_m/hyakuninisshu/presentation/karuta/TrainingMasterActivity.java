package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.ActivityTrainingMasterBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.AlertDialogFragment;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.QuizMasterActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
import me.rei_m.hyakuninisshu.presentation.karuta.module.QuizMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultFragment;
import me.rei_m.hyakuninisshu.presentation.utilitty.ViewUtil;

public class TrainingMasterActivity extends BaseActivity implements TrainingMasterContact.View,
        HasComponent<QuizMasterActivityComponent>,
        QuizFragment.OnFragmentInteractionListener,
        QuizAnswerFragment.OnFragmentInteractionListener,
        QuizResultFragment.OnFragmentInteractionListener,
        AlertDialogFragment.OnClickPositiveButtonListener {

    public static Intent createIntent(@NonNull Context context,
                                      @NonNull TrainingRangeFrom trainingRangeFrom,
                                      @NonNull TrainingRangeTo trainingRangeTo,
                                      @NonNull Kimariji kimariji,
                                      @NonNull Color color,
                                      @NonNull KarutaStyle topPhraseStyle,
                                      @NonNull KarutaStyle bottomPhraseStyle) {
        Intent intent = new Intent(context, TrainingMasterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TRAINING_RANGE_FROM, trainingRangeFrom);
        bundle.putSerializable(ARG_TRAINING_RANGE_TO, trainingRangeTo);
        bundle.putSerializable(ARG_KIMARIJI, kimariji);
        bundle.putSerializable(ARG_COLOR, color);
        bundle.putSerializable(ARG_TOP_PHRASE_STYLE, topPhraseStyle);
        bundle.putSerializable(ARG_BOTTOM_PHRASE_STYLE, bottomPhraseStyle);
        intent.putExtras(bundle);
        return intent;
    }

    private static final String ARG_TRAINING_RANGE_FROM = "trainingRangeFrom";

    private static final String ARG_TRAINING_RANGE_TO = "trainingRangeTo";

    private static final String ARG_KIMARIJI = "kimarij";

    private static final String ARG_COLOR = "color";

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

        TrainingRangeFrom trainingRangeFrom = (TrainingRangeFrom) getIntent().getSerializableExtra(ARG_TRAINING_RANGE_FROM);
        TrainingRangeTo trainingRangeTo = (TrainingRangeTo) getIntent().getSerializableExtra(ARG_TRAINING_RANGE_TO);
        Kimariji kimariji = (Kimariji) getIntent().getSerializableExtra(ARG_KIMARIJI);
        Color color = (Color) getIntent().getSerializableExtra(ARG_COLOR);

        if (savedInstanceState == null) {
            presenter.onCreate(this,
                    trainingRangeFrom,
                    trainingRangeTo,
                    kimariji,
                    color);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
    public void onErrorQuiz() {
        DialogFragment newFragment = AlertDialogFragment.newInstance(
                R.string.text_title_error,
                R.string.text_message_quiz_error);
        newFragment.show(getSupportFragmentManager(), "dialog");
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

    @Override
    public void onClickPositiveButton() {
        finish();
    }
}
