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
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.TrainingRangeTo;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsActivityViewModel;

public class TrainingMasterActivityViewModel extends AbsActivityViewModel {

    public final ObservableBoolean isVisibleEmpty = new ObservableBoolean(false);

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(false);

    private final PublishSubject<Unit> startTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> startTrainingEvent = startTrainingEventSubject;

    private final KarutaTrainingModel karutaTrainingModel;

    private TrainingRangeFrom trainingRangeFrom;
    private TrainingRangeTo trainingRangeTo;
    private KimarijiFilter kimarijiFilter;
    private ColorFilter colorFilter;

    private boolean isStartedTraining = false;

    public TrainingMasterActivityViewModel(@NonNull KarutaTrainingModel karutaTrainingModel) {
        this.karutaTrainingModel = karutaTrainingModel;
    }

    public boolean isStartedTraining() {
        return isStartedTraining;
    }

    public void onCreate(@NonNull TrainingRangeFrom trainingRangeFrom,
                         @NonNull TrainingRangeTo trainingRangeTo,
                         @NonNull KimarijiFilter kimarijiFilter,
                         @NonNull ColorFilter colorFilter) {
        this.trainingRangeFrom = trainingRangeFrom;
        this.trainingRangeTo = trainingRangeTo;
        this.kimarijiFilter = kimarijiFilter;
        this.colorFilter = colorFilter;
    }

    public void onReCreate(@NonNull TrainingRangeFrom trainingRangeFrom,
                           @NonNull TrainingRangeTo trainingRangeTo,
                           @NonNull KimarijiFilter kimarijiFilter,
                           @NonNull ColorFilter colorFilter,
                           boolean isStartedTraining) {
        this.trainingRangeFrom = trainingRangeFrom;
        this.trainingRangeTo = trainingRangeTo;
        this.kimarijiFilter = kimarijiFilter;
        this.colorFilter = colorFilter;
        this.isStartedTraining = isStartedTraining;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaTrainingModel.completeStartEvent.subscribe(v -> {
            isStartedTraining = true;
            isVisibleEmpty.set(false);
            isVisibleAd.set(false);
            startTrainingEventSubject.onNext(Unit.INSTANCE);
        }), karutaTrainingModel.notFoundErrorEvent.subscribe(v -> {
            isVisibleEmpty.set(true);
            isVisibleAd.set(true);
        }));

        if (!isStartedTraining) {
            karutaTrainingModel.start(trainingRangeFrom.identifier(),
                    trainingRangeTo.identifier(),
                    kimarijiFilter.value(),
                    colorFilter.value());
        }
    }

    public void onClickGoToResult() {
        isVisibleAd.set(true);
    }

    public void onRestartTraining() {
        isVisibleAd.set(false);
    }
}
