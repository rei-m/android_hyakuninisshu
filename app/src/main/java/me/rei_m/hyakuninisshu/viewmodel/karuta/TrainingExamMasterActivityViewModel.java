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

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.action.training.TrainingActionDispatcher;
import me.rei_m.hyakuninisshu.store.TrainingStore;
import me.rei_m.hyakuninisshu.util.Unit;

public class TrainingExamMasterActivityViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final TrainingStore trainingStore;
        private final TrainingActionDispatcher actionDispatcher;

        public Factory(@NonNull TrainingStore trainingStore,
                       @NonNull TrainingActionDispatcher actionDispatcher) {
            this.trainingStore = trainingStore;
            this.actionDispatcher = actionDispatcher;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(TrainingExamMasterActivityViewModel.class)) {
                return (T) new TrainingExamMasterActivityViewModel(trainingStore, actionDispatcher);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableBoolean isVisibleEmpty = new ObservableBoolean(false);

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(true);

    private final PublishSubject<Unit> startedTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> startedTrainingEvent = startedTrainingEventSubject;

    private final PublishSubject<Boolean> toggledAdEventSubject = PublishSubject.create();
    public final Observable<Boolean> toggledAdEvent = toggledAdEventSubject;

    private final TrainingActionDispatcher actionDispatcher;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public TrainingExamMasterActivityViewModel(@NonNull TrainingStore trainingStore,
                                               @NonNull TrainingActionDispatcher actionDispatcher) {
        this.actionDispatcher = actionDispatcher;
        disposable.addAll(trainingStore.startedEvent.subscribe(v -> {
            isVisibleEmpty.set(false);
            isVisibleAd.set(false);
            startedTrainingEventSubject.onNext(Unit.INSTANCE);
        }), trainingStore.notFoundErrorEvent.subscribe(v -> {
            isVisibleEmpty.set(true);
            isVisibleAd.set(true);
        }));

        isVisibleAd.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable observable, int i) {
                toggledAdEventSubject.onNext(isVisibleAd.get());
            }
        });

        actionDispatcher.startForExam();
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public void onRestartTraining() {
        actionDispatcher.restartForPractice();
    }
}
