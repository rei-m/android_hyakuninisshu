package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityTrainingMasterBinding;
import me.rei_m.hyakuninisshu.presentation.AlertDialogFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Color;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.TrainingMasterActivityViewModel;

public class TrainingMasterActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        QuizFragment.OnFragmentInteractionListener,
        QuizAnswerFragment.OnFragmentInteractionListener,
        QuizResultFragment.OnFragmentInteractionListener,
        AlertDialogFragment.OnDialogInteractionListener {

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

    private static final String KEY_IS_STARTED = "isStarted";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Inject
    TrainingMasterActivityViewModel viewModel;

    private ActivityTrainingMasterBinding binding;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_training_master);

        setSupportActionBar(binding.toolbar);

        TrainingRangeFrom trainingRangeFrom = (TrainingRangeFrom) getIntent().getSerializableExtra(ARG_TRAINING_RANGE_FROM);
        TrainingRangeTo trainingRangeTo = (TrainingRangeTo) getIntent().getSerializableExtra(ARG_TRAINING_RANGE_TO);
        Kimariji kimariji = (Kimariji) getIntent().getSerializableExtra(ARG_KIMARIJI);
        Color color = (Color) getIntent().getSerializableExtra(ARG_COLOR);

        binding.setViewModel(viewModel);

        if (savedInstanceState == null) {
            viewModel.onCreate(trainingRangeFrom,
                    trainingRangeTo,
                    kimariji,
                    color);
        } else {
            boolean isStarted = savedInstanceState.getBoolean(KEY_IS_STARTED, false);
            viewModel.onReCreate(trainingRangeFrom,
                    trainingRangeTo,
                    kimariji,
                    color,
                    isStarted);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        disposable = new CompositeDisposable();
        disposable.add(viewModel.startTrainingEvent.subscribe(unit -> {
            KarutaStyle topPhraseStyle = (KarutaStyle) getIntent().getSerializableExtra(ARG_TOP_PHRASE_STYLE);
            KarutaStyle bottomPhraseStyle = (KarutaStyle) getIntent().getSerializableExtra(ARG_BOTTOM_PHRASE_STYLE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, QuizFragment.newInstance(topPhraseStyle, bottomPhraseStyle), QuizFragment.TAG)
                    .commit();
        }));
        viewModel.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        viewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        fragmentInjector = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_STARTED, viewModel.isStartedTraining());
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onAnswered(long karutaId, boolean existNextQuiz) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.content, QuizAnswerFragment.newInstance(karutaId, existNextQuiz), QuizAnswerFragment.TAG)
                .commit();
    }

    @Override
    public void onErrorQuiz() {
        DialogFragment newFragment = AlertDialogFragment.newInstance(
                R.string.text_title_error,
                R.string.text_message_quiz_error,
                true,
                false);
        newFragment.show(getSupportFragmentManager(), AlertDialogFragment.TAG);
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
        viewModel.onClickGoToResult();

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.content, QuizResultFragment.newInstance(), QuizResultFragment.TAG)
                .commit();
    }

    @Override
    public void onReceiveIllegalArguments() {
        DialogFragment newFragment = AlertDialogFragment.newInstance(
                R.string.text_title_error,
                R.string.text_message_illegal_arguments,
                true,
                false);
        newFragment.show(getSupportFragmentManager(), AlertDialogFragment.TAG);
    }

    @Override
    public void onRestartTraining() {
        viewModel.onRestartTraining();

        KarutaStyle topPhraseStyle = (KarutaStyle) getIntent().getSerializableExtra(ARG_TOP_PHRASE_STYLE);
        KarutaStyle bottomPhraseStyle = (KarutaStyle) getIntent().getSerializableExtra(ARG_BOTTOM_PHRASE_STYLE);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, QuizFragment.newInstance(topPhraseStyle, bottomPhraseStyle), QuizFragment.TAG)
                .commit();
    }

    @Override
    public void onFinishTraining() {
        finish();
    }

    @Override
    public void onDialogPositiveClick() {
        finish();
    }

    @Override
    public void onDialogNegativeClick() {

    }
}
