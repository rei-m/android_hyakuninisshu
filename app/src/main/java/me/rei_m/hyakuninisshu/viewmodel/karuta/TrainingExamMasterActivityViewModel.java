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

import android.databinding.ObservableBoolean;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsActivityViewModel;

public class TrainingExamMasterActivityViewModel extends AbsActivityViewModel {

    public final ObservableBoolean isVisibleEmpty = new ObservableBoolean(false);

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(false);

    private final PublishSubject<Unit> startTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> startTrainingEvent = startTrainingEventSubject;

    private final KarutaTrainingModel karutaTrainingModel;

    private boolean isStartedTraining = false;

    public TrainingExamMasterActivityViewModel(KarutaTrainingModel karutaTrainingModel) {
        this.karutaTrainingModel = karutaTrainingModel;
    }

    public boolean isStartedTraining() {
        return isStartedTraining;
    }

    public void onReCreate(boolean isStartedTraining) {
        this.isStartedTraining = isStartedTraining;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaTrainingModel.completeStartForExamEvent.subscribe(v -> {
            isStartedTraining = true;
            startTrainingEventSubject.onNext(Unit.INSTANCE);
        }), karutaTrainingModel.notFoundErrorEvent.subscribe(v -> {
            isVisibleEmpty.set(true);
            isVisibleAd.set(true);
        }));

        if (!isStartedTraining) {
            karutaTrainingModel.startForExam();
        }
    }

    public void onClickGoToResult() {
        isVisibleAd.set(true);
    }

    public void onRestartTraining() {
        isVisibleAd.set(false);
    }
}
