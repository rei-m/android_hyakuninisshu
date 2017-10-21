package me.rei_m.hyakuninisshu.presentation.karuta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import dagger.Binds;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityExamMasterBinding;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.presentation.AlertDialogFragment;
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamResultFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.ExamMasterActivityViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.di.ExamMasterActivityViewModelModule;

public class ExamMasterActivity extends DaggerAppCompatActivity implements QuizFragment.OnFragmentInteractionListener,
        QuizAnswerFragment.OnFragmentInteractionListener,
        ExamResultFragment.OnFragmentInteractionListener,
        AlertDialogFragment.OnDialogInteractionListener {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, ExamMasterActivity.class);
    }

    @ForActivity
    @dagger.Subcomponent(modules = {
            ActivityModule.class,
            ExamMasterActivityViewModelModule.class,
            QuizAnswerFragment.Module.class,
            QuizFragment.Module.class,
            ExamResultFragment.Module.class
    })
    public interface Subcomponent extends AndroidInjector<ExamMasterActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<ExamMasterActivity> {

            public abstract Builder activityModule(ActivityModule module);

            @Override
            public void seedInstance(ExamMasterActivity instance) {
                activityModule(new ActivityModule(instance));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(ExamMasterActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bind(Subcomponent.Builder builder);
    }

    @Inject
    ExamMasterActivityViewModel viewModel;

    private ActivityExamMasterBinding binding;

    private CompositeDisposable disposable;

    private static final String KEY_IS_STARTED = "isStarted";

    private static final String KEY_IS_FINISHED = "isFinished";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam_master);
        binding.setViewModel(viewModel);

        setSupportActionBar(binding.toolbar);

        if (savedInstanceState != null) {
            boolean isStarted = savedInstanceState.getBoolean(KEY_IS_STARTED, false);
            boolean isFinished = savedInstanceState.getBoolean(KEY_IS_FINISHED, false);
            viewModel.onReCreate(isStarted, isFinished);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel = null;
        binding = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        disposable = new CompositeDisposable();
        disposable.addAll(
                viewModel.startExamEvent.subscribe(v -> getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content, QuizFragment.newInstance(KarutaStyleFilter.KANJI, KarutaStyleFilter.KANA), QuizFragment.TAG)
                        .commit()),
                viewModel.aggregateExamResultsEvent.subscribe(examId -> getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.content, ExamResultFragment.newInstance(examId), ExamResultFragment.TAG)
                        .commit())
        );
        viewModel.onStart();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_STARTED, viewModel.isStartedExam());
        outState.putBoolean(KEY_IS_FINISHED, viewModel.isFinishedExam());
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
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onClickGoToNext() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content, QuizFragment.newInstance(KarutaStyleFilter.KANJI, KarutaStyleFilter.KANA), QuizFragment.TAG)
                .commit();
    }

    @Override
    public void onClickGoToResult() {
        viewModel.onClickGoToResult();
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
    public void onFinishExam() {
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
