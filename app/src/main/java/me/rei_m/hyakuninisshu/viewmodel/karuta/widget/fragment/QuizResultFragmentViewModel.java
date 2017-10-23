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

package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.view.View;

import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.event.EventObservable;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class QuizResultFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<String> score = new ObservableField<>("");

    public final ObservableFloat averageAnswerTime = new ObservableFloat();

    public final ObservableBoolean canRestartTraining = new ObservableBoolean();

    public final EventObservable<Unit> restartEvent = EventObservable.create();

    public final EventObservable<Unit> onClickBackMenuEvent = EventObservable.create();

    public final EventObservable<Unit> errorEvent = EventObservable.create();

    private final KarutaTrainingModel karutaTrainingModel;

    private final AnalyticsManager analyticsManager;

    public QuizResultFragmentViewModel(@NonNull KarutaTrainingModel karutaTrainingModel,
                                       @NonNull AnalyticsManager analyticsManager) {
        this.karutaTrainingModel = karutaTrainingModel;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaTrainingModel.completeAggregateResultsEvent.subscribe(trainingResult -> {
            score.set(trainingResult.correctCount() + "/" + trainingResult.quizCount());
            averageAnswerTime.set(trainingResult.averageAnswerTime());
            canRestartTraining.set(trainingResult.canRestartTraining());
        }), karutaTrainingModel.completeRestartEvent.subscribe(v -> restartEvent.onNext(Unit.INSTANCE)));
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.QUIZ_RESULT);
        karutaTrainingModel.aggregateResults();
    }

    @SuppressWarnings("unused")
    public void onClickPracticeWrongKarutas(View view) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.RESTART_TRAINING);
        karutaTrainingModel.restartForPractice();
    }

    @SuppressWarnings("unused")
    public void onClickBackMenu(View view) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.FINISH_TRAINING);
        onClickBackMenuEvent.onNext(Unit.INSTANCE);
    }
}
