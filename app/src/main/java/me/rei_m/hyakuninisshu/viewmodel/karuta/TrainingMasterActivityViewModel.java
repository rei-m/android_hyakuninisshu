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

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeTo;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;

public class TrainingMasterActivityViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaTrainingModel karutaTrainingModel;

        private final TrainingRangeFrom trainingRangeFrom;

        private final TrainingRangeTo trainingRangeTo;

        private final KimarijiFilter kimarijiFilter;

        private final ColorFilter colorFilter;

        public Factory(@NonNull KarutaTrainingModel karutaTrainingModel,
                       @NonNull TrainingRangeFrom trainingRangeFrom,
                       @NonNull TrainingRangeTo trainingRangeTo,
                       @NonNull KimarijiFilter kimarijiFilter,
                       @NonNull ColorFilter colorFilter) {
            this.karutaTrainingModel = karutaTrainingModel;
            this.trainingRangeFrom = trainingRangeFrom;
            this.trainingRangeTo = trainingRangeTo;
            this.kimarijiFilter = kimarijiFilter;
            this.colorFilter = colorFilter;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public TrainingMasterActivityViewModel create(@NonNull Class modelClass) {
            return new TrainingMasterActivityViewModel(karutaTrainingModel,
                    trainingRangeFrom,
                    trainingRangeTo,
                    kimarijiFilter,
                    colorFilter);
        }
    }

    public final ObservableBoolean isVisibleEmpty = new ObservableBoolean(false);

    public final EventObservable<Unit> startTrainingEvent = EventObservable.create();

    public final EventObservable<Boolean> toggleAdEvent = EventObservable.create();

    private boolean isVisibleAd = false;

    private CompositeDisposable disposable = null;

    public TrainingMasterActivityViewModel(@NonNull KarutaTrainingModel karutaTrainingModel,
                                           @NonNull TrainingRangeFrom trainingRangeFrom,
                                           @NonNull TrainingRangeTo trainingRangeTo,
                                           @NonNull KimarijiFilter kimarijiFilter,
                                           @NonNull ColorFilter colorFilter) {
        disposable = new CompositeDisposable();
        disposable.addAll(karutaTrainingModel.completeStartEvent.subscribe(v -> {
            isVisibleEmpty.set(false);
            toggleAdEvent.onNext(false);
            startTrainingEvent.onNext(Unit.INSTANCE);
        }), karutaTrainingModel.notFoundErrorEvent.subscribe(v -> {
            isVisibleEmpty.set(true);
            toggleAdEvent.onNext(true);
        }), toggleAdEvent.subscribe(isVisible -> {
            isVisibleAd = isVisible;
        }));

        karutaTrainingModel.start(trainingRangeFrom.identifier(),
                trainingRangeTo.identifier(),
                kimarijiFilter.value(),
                colorFilter.value());
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
    }

    public boolean isVisibleAd() {
        return isVisibleAd;
    }

    public void onClickGoToResult() {
        toggleAdEvent.onNext(true);
    }

    public void onRestartTraining() {
        toggleAdEvent.onNext(false);
    }
}
