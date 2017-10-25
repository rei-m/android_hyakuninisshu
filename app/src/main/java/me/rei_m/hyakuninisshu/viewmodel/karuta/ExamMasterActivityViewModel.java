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

package me.rei_m.hyakuninisshu.viewmodel.karuta;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.event.EventObservable;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsActivityViewModel;

public class ExamMasterActivityViewModel extends AbsActivityViewModel {

    public final EventObservable<Unit> startExamEvent = EventObservable.create();

    public final EventObservable<KarutaExamIdentifier> aggregateExamResultsEvent = EventObservable.create();

    public final EventObservable<Boolean> toggleAdEvent = EventObservable.create();

    private final KarutaExamModel karutaExamModel;

    private boolean isStartedExam = false;
    private boolean isFinishedExam = false;

    public ExamMasterActivityViewModel(@NonNull KarutaExamModel karutaExamModel) {
        this.karutaExamModel = karutaExamModel;
    }

    public boolean isStartedExam() {
        return isStartedExam;
    }

    public boolean isFinishedExam() {
        return isFinishedExam;
    }

    public void onReCreate(boolean isStartedExam,
                           boolean isFinishedExam) {
        this.isStartedExam = isStartedExam;
        this.isFinishedExam = isFinishedExam;
        this.toggleAdEvent.onNext(isFinishedExam);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaExamModel.completeStartEvent.subscribe(v -> {
            isStartedExam = true;
            toggleAdEvent.onNext(false);
            startExamEvent.onNext(Unit.INSTANCE);
        }), karutaExamModel.completeAggregateResultsEvent.subscribe(karutaExamId -> {
            isFinishedExam = true;
            toggleAdEvent.onNext(true);
            aggregateExamResultsEvent.onNext(karutaExamId);
        }));

        if (!isStartedExam) {
            karutaExamModel.start();
        }
    }

    public void onClickGoToResult() {
        karutaExamModel.aggregateResults();
    }
}
