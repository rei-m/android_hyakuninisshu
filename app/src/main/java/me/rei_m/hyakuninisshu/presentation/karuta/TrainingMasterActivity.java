/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu.presentation.karuta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdView;

import javax.inject.Inject;

import dagger.Binds;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.ActivityTrainingMasterBinding;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.presentation.AlertDialogFragment;
import me.rei_m.hyakuninisshu.presentation.ad.AdViewFactory;
import me.rei_m.hyakuninisshu.presentation.ad.AdViewHelper;
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeTo;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.TrainingMasterActivityViewModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.di.TrainingMasterActivityViewModelModule;

public class TrainingMasterActivity extends DaggerAppCompatActivity implements QuizFragment.OnFragmentInteractionListener,
        QuizAnswerFragment.OnFragmentInteractionListener,
        QuizResultFragment.OnFragmentInteractionListener,
        AlertDialogFragment.OnDialogInteractionListener {

    public static Intent createIntent(@NonNull Context context,
                                      @NonNull TrainingRangeFrom trainingRangeFrom,
                                      @NonNull TrainingRangeTo trainingRangeTo,
                                      @NonNull KimarijiFilter kimarijiFilter,
                                      @NonNull ColorFilter colorFilter,
                                      @NonNull KarutaStyleFilter kamiNoKuStyle,
                                      @NonNull KarutaStyleFilter shimoNoKuStyle) {
        Intent intent = new Intent(context, TrainingMasterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TRAINING_RANGE_FROM, trainingRangeFrom.ordinal());
        bundle.putInt(ARG_TRAINING_RANGE_TO, trainingRangeTo.ordinal());
        bundle.putInt(ARG_KIMARIJI, kimarijiFilter.ordinal());
        bundle.putInt(ARG_COLOR, colorFilter.ordinal());
        bundle.putInt(ARG_KAMI_NO_KU_STYLE, kamiNoKuStyle.ordinal());
        bundle.putInt(ARG_SHIMO_NO_KU_STYLE, shimoNoKuStyle.ordinal());
        intent.putExtras(bundle);
        return intent;
    }

    private static final String ARG_TRAINING_RANGE_FROM = "trainingRangeFrom";

    private static final String ARG_TRAINING_RANGE_TO = "trainingRangeTo";

    private static final String ARG_KIMARIJI = "kimarij";

    private static final String ARG_COLOR = "color";

    private static final String ARG_KAMI_NO_KU_STYLE = "kamiNoKuStyle";

    private static final String ARG_SHIMO_NO_KU_STYLE = "shimoNoKuStyle";

    private static final String KEY_IS_STARTED = "isStarted";

    @ForActivity
    @dagger.Subcomponent(modules = {
            ActivityModule.class,
            TrainingMasterActivityViewModelModule.class,
            QuizFragment.Module.class,
            QuizAnswerFragment.Module.class,
            QuizResultFragment.Module.class
    })
    public interface Subcomponent extends AndroidInjector<TrainingMasterActivity> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<TrainingMasterActivity> {

            public abstract Builder activityModule(ActivityModule module);

            @Override
            public void seedInstance(TrainingMasterActivity instance) {
                activityModule(new ActivityModule(instance));
            }
        }
    }

    @dagger.Module(subcomponents = Subcomponent.class)
    public abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(TrainingMasterActivity.class)
        abstract AndroidInjector.Factory<? extends Activity> bind(Subcomponent.Builder builder);
    }

    @Inject
    AdViewFactory adViewFactory;

    @Inject
    TrainingMasterActivityViewModel viewModel;

    private ActivityTrainingMasterBinding binding;

    private AdView adView;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_training_master);

        setSupportActionBar(binding.toolbar);

        TrainingRangeFrom trainingRangeFrom = TrainingRangeFrom.get(getIntent().getIntExtra(ARG_TRAINING_RANGE_FROM, 0));
        TrainingRangeTo trainingRangeTo = TrainingRangeTo.get(getIntent().getIntExtra(ARG_TRAINING_RANGE_TO, 0));
        KimarijiFilter kimarijiFilter = KimarijiFilter.get(getIntent().getIntExtra(ARG_KIMARIJI, 0));
        ColorFilter colorFilter = ColorFilter.get(getIntent().getIntExtra(ARG_COLOR, 0));

        binding.setViewModel(viewModel);

        if (savedInstanceState == null) {
            viewModel.onCreate(trainingRangeFrom,
                    trainingRangeTo,
                    kimarijiFilter,
                    colorFilter);
        } else {
            boolean isStarted = savedInstanceState.getBoolean(KEY_IS_STARTED, false);
            viewModel.onReCreate(trainingRangeFrom,
                    trainingRangeTo,
                    kimarijiFilter,
                    colorFilter,
                    isStarted);
        }

        adView = adViewFactory.create();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, adView.getId());
        adView.setLayoutParams(params);
        binding.root.addView(adView);
        adView.setVisibility(View.GONE);

        AdViewHelper.loadAd(adView);
    }

    @Override
    protected void onDestroy() {
        AdViewHelper.release(adView);

        adView.destroy();
        adView = null;

        adViewFactory = null;
        viewModel = null;
        binding = null;
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        disposable = new CompositeDisposable();
        disposable.addAll(viewModel.startTrainingEvent.subscribe(unit -> {
            KarutaStyleFilter kamiNoKuStyle = KarutaStyleFilter.get(getIntent().getIntExtra(ARG_KAMI_NO_KU_STYLE, 0));
            KarutaStyleFilter shimoNoKuStyle = KarutaStyleFilter.get(getIntent().getIntExtra(ARG_SHIMO_NO_KU_STYLE, 0));
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, QuizFragment.newInstance(kamiNoKuStyle, shimoNoKuStyle), QuizFragment.TAG)
                    .commit();
        }), viewModel.toggleAdEvent.subscribe(isVisible -> {
            if (isVisible) {
                adView.setVisibility(View.VISIBLE);
            } else {
                adView.setVisibility(View.GONE);
            }
        }));
        viewModel.onStart();
    }

    @Override
    protected void onStop() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        viewModel.onStop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adView.resume();
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        adView.pause();
        viewModel.onPause();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_STARTED, viewModel.isStartedTraining());
    }

    @Override
    public void onAnswered(@NonNull KarutaIdentifier karutaId, boolean existNextQuiz) {
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

        KarutaStyleFilter kamiNoKuStyle = KarutaStyleFilter.get(getIntent().getIntExtra(ARG_KAMI_NO_KU_STYLE, 0));
        KarutaStyleFilter shimoNoKuStyle = KarutaStyleFilter.get(getIntent().getIntExtra(ARG_SHIMO_NO_KU_STYLE, 0));

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content, QuizFragment.newInstance(kamiNoKuStyle, shimoNoKuStyle), QuizFragment.TAG)
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

        KarutaStyleFilter kamiNoKuStyle = KarutaStyleFilter.get(getIntent().getIntExtra(ARG_KAMI_NO_KU_STYLE, 0));
        KarutaStyleFilter shimoNoKuStyle = KarutaStyleFilter.get(getIntent().getIntExtra(ARG_SHIMO_NO_KU_STYLE, 0));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, QuizFragment.newInstance(kamiNoKuStyle, shimoNoKuStyle), QuizFragment.TAG)
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
