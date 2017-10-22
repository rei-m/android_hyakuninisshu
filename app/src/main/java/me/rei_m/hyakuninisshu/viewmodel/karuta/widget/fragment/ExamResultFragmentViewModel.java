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

import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class ExamResultFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<String> score = new ObservableField<>();

    public final ObservableFloat averageAnswerTime = new ObservableFloat();

    public final ObservableField<boolean[]> karutaQuizResultList = new ObservableField<>();

    private final PublishSubject<Unit> onClickBackMenuEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickBackMenuEvent = onClickBackMenuEventSubject;

    private final KarutaExamModel karutaExamModel;

    private final Navigator navigator;

    private final AnalyticsManager analyticsManager;

    private KarutaExamIdentifier karutaExamIdentifier;

    public ExamResultFragmentViewModel(@NonNull KarutaExamModel karutaExamModel,
                                       @NonNull Navigator navigator,
                                       @NonNull AnalyticsManager analyticsManager) {
        this.karutaExamModel = karutaExamModel;
        this.navigator = navigator;
        this.analyticsManager = analyticsManager;
    }

    public void onCreate(long karutaExamId) {
        karutaExamIdentifier = new KarutaExamIdentifier(karutaExamId);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaExamModel.completeFetchResultEvent.subscribe(examResult -> {
            score.set(examResult.score());
            averageAnswerTime.set(examResult.averageAnswerTime());
            karutaQuizResultList.set(examResult.resultList());
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM_RESULT);
        karutaExamModel.fetchResult(karutaExamIdentifier);
    }

    @SuppressWarnings("unused")
    public void onClickBackMenu(View view) {
        onClickBackMenuEventSubject.onNext(Unit.INSTANCE);
    }

    public void onClickResult(int karutaNo) {
        navigator.navigateToMaterialSingle(karutaNo);
    }
}
