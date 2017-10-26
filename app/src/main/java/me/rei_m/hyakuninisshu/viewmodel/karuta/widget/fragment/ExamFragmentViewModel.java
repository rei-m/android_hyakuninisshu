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
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class ExamFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableBoolean hasResult = new ObservableBoolean(false);

    public final ObservableField<String> score = new ObservableField<>("");

    public final ObservableFloat averageAnswerTime = new ObservableFloat();

    private final KarutaExamModel karutaExamModel;

    private final Navigator navigator;

    private final AnalyticsManager analyticsManager;

    public ExamFragmentViewModel(@NonNull KarutaExamModel karutaExamModel,
                                 @NonNull Navigator navigator,
                                 @NonNull AnalyticsManager analyticsManager) {
        this.karutaExamModel = karutaExamModel;
        this.navigator = navigator;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaExamModel.completeFetchResultEvent.subscribe(examResult -> {
            hasResult.set(true);
            score.set(examResult.score());
            averageAnswerTime.set(examResult.averageAnswerTime());
        }), karutaExamModel.notFoundResultEvent.subscribe(v -> hasResult.set(false)));
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM);
        karutaExamModel.fetchRecentResult();
    }

    @SuppressWarnings("unused")
    public void onClickStartExam(View view) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_EXAM);
        navigator.navigateToExamMaster();
    }

    @SuppressWarnings("unused")
    public void onClickStartTraining(View view) {
        analyticsManager.logActionEvent(AnalyticsManager.ActionEvent.START_TRAINING_FOR_EXAM);
        navigator.navigateToExamTrainingMaster();
    }
}
