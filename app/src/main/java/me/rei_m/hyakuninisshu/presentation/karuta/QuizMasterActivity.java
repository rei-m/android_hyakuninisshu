package me.rei_m.hyakuninisshu.presentation.karuta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.BaseActivity;

public class QuizMasterActivity extends BaseActivity implements QuizMasterContact.View,
        QuizFragment.OnFragmentInteractionListener,
        QuizAnswerFragment.OnFragmentInteractionListener {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, QuizMasterActivity.class);
    }

    @Inject
    ActivityNavigator activityNavigator;

    @Inject
    QuizMasterContact.Actions presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_quiz_master);
        presenter.onCreate(this, savedInstanceState);
    }

    @Override
    public void startQuiz() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, QuizFragment.newInstance(), QuizFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onAnswered(String quizId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, QuizAnswerFragment.newInstance(quizId), QuizAnswerFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onClickGoToNext() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, QuizFragment.newInstance(), QuizFragment.class.getSimpleName())
                .commit();
    }
}
