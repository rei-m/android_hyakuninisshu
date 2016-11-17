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
import me.rei_m.hyakuninisshu.databinding.ActivityQuizMasterBinding;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.QuizMasterActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.Kimariji;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRange;
import me.rei_m.hyakuninisshu.presentation.karuta.module.QuizMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultFragment;

public class QuizMasterActivity extends BaseActivity implements QuizMasterContact.View,
        HasComponent<QuizMasterActivityComponent>,
        QuizFragment.OnFragmentInteractionListener,
        QuizAnswerFragment.OnFragmentInteractionListener {

    public static Intent createIntent(@NonNull Context context, TrainingRange trainingRange, Kimariji kimariji) {
        Intent intent = new Intent(context, QuizMasterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TRAINING_RANGE, trainingRange);
        bundle.putSerializable(ARG_KIMARIJI, kimariji);
        intent.putExtras(bundle);
        return intent;
    }

    private static final String ARG_TRAINING_RANGE = "trainingRange";

    private static final String ARG_KIMARIJI = "kimarij";

    private QuizMasterActivityComponent component;

    @Inject
    ActivityNavigator activityNavigator;

    @Inject
    QuizMasterContact.Actions presenter;

    private ActivityQuizMasterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_master);

        TrainingRange trainingRange = (TrainingRange) getIntent().getSerializableExtra(ARG_TRAINING_RANGE);
        Kimariji kimariji = (Kimariji) getIntent().getSerializableExtra(ARG_KIMARIJI);

        if (savedInstanceState == null) {
            presenter.onCreate(this, trainingRange, kimariji);
        }
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
    public void startQuiz() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, QuizFragment.newInstance(), QuizFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void showEmpty() {
        binding.layoutEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnswered(@NonNull String quizId) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.content, QuizAnswerFragment.newInstance(quizId), QuizAnswerFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onClickGoToNext() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content, QuizFragment.newInstance(), QuizFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onClickGoToResult() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.content, QuizResultFragment.newInstance(), QuizResultFragment.class.getSimpleName())
                .commit();
    }
}
