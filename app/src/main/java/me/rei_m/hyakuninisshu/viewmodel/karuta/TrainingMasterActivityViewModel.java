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
import me.rei_m.hyakuninisshu.action.training.TrainingActionDispatcher;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.ColorFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KimarijiFilter;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeFrom;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.TrainingRangeTo;
import me.rei_m.hyakuninisshu.store.TrainingStore;

public class TrainingMasterActivityViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final TrainingStore trainingStore;
        private final TrainingActionDispatcher actionDispatcher;
        private final TrainingRangeFrom trainingRangeFrom;
        private final TrainingRangeTo trainingRangeTo;
        private final KimarijiFilter kimarijiFilter;
        private final ColorFilter colorFilter;
        private boolean isStarted = false;

        public Factory(@NonNull TrainingStore trainingStore,
                       @NonNull TrainingActionDispatcher actionDispatcher,
                       @NonNull TrainingRangeFrom trainingRangeFrom,
                       @NonNull TrainingRangeTo trainingRangeTo,
                       @NonNull KimarijiFilter kimarijiFilter,
                       @NonNull ColorFilter colorFilter) {
            this.trainingStore = trainingStore;
            this.actionDispatcher = actionDispatcher;
            this.trainingRangeFrom = trainingRangeFrom;
            this.trainingRangeTo = trainingRangeTo;
            this.kimarijiFilter = kimarijiFilter;
            this.colorFilter = colorFilter;
        }

        public void setStarted(boolean started) {
            isStarted = started;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(TrainingMasterActivityViewModel.class)) {
                return (T) new TrainingMasterActivityViewModel(trainingStore,
                        actionDispatcher,
                        trainingRangeFrom,
                        trainingRangeTo,
                        kimarijiFilter,
                        colorFilter,
                        isStarted);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableBoolean isVisibleEmpty = new ObservableBoolean(false);

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(true);

    public final ObservableBoolean isStarted = new ObservableBoolean(false);

    private final TrainingActionDispatcher actionDispatcher;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public TrainingMasterActivityViewModel(@NonNull TrainingStore trainingStore,
                                           @NonNull TrainingActionDispatcher actionDispatcher,
                                           @NonNull TrainingRangeFrom trainingRangeFrom,
                                           @NonNull TrainingRangeTo trainingRangeTo,
                                           @NonNull KimarijiFilter kimarijiFilter,
                                           @NonNull ColorFilter colorFilter,
                                           boolean isStarted) {
        this.actionDispatcher = actionDispatcher;
        disposable.addAll(trainingStore.startedEvent.subscribe(v -> {
            isVisibleEmpty.set(false);
            isVisibleAd.set(false);
            this.isStarted.set(true);
        }), trainingStore.notFoundErrorEvent.subscribe(v -> {
            isVisibleEmpty.set(true);
            isVisibleAd.set(true);
            this.isStarted.set(false);
        }));

        if (isStarted) {
            isVisibleAd.set(false);
            this.isStarted.set(true);
        } else {
            actionDispatcher.start(trainingRangeFrom.identifier(),
                    trainingRangeTo.identifier(),
                    kimarijiFilter.value(),
                    colorFilter.value());
        }
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public void onRestartTraining() {
        isStarted.set(false);
        actionDispatcher.restartForPractice();
    }
}
