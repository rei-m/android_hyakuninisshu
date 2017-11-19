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
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.util.Unit;

public class TrainingExamMasterActivityViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaTrainingModel karutaTrainingModel;

        public Factory(@NonNull KarutaTrainingModel karutaTrainingModel) {
            this.karutaTrainingModel = karutaTrainingModel;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public TrainingExamMasterActivityViewModel create(@NonNull Class modelClass) {
            return new TrainingExamMasterActivityViewModel(karutaTrainingModel);
        }
    }

    public final ObservableBoolean isVisibleEmpty = new ObservableBoolean(false);

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(true);

    private final PublishSubject<Unit> startedTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> startedTrainingEvent = startedTrainingEventSubject;

    private final PublishSubject<Boolean> toggledAdEventSubject = PublishSubject.create();
    public final Observable<Boolean> toggledAdEvent = toggledAdEventSubject;

    private final KarutaTrainingModel karutaTrainingModel;

    private CompositeDisposable disposable = null;

    public TrainingExamMasterActivityViewModel(@NonNull KarutaTrainingModel karutaTrainingModel) {
        this.karutaTrainingModel = karutaTrainingModel;
        disposable = new CompositeDisposable();
        disposable.addAll(karutaTrainingModel.startedEvent.subscribe(v -> {
            isVisibleEmpty.set(false);
            isVisibleAd.set(false);
            startedTrainingEventSubject.onNext(Unit.INSTANCE);
        }), karutaTrainingModel.notFoundErrorEvent.subscribe(v -> {
            isVisibleEmpty.set(true);
            isVisibleAd.set(true);
        }));

        isVisibleAd.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable observable, int i) {
                toggledAdEventSubject.onNext(isVisibleAd.get());
            }
        });

        karutaTrainingModel.startForExam();
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
    }

    public void onRestartTraining() {
        karutaTrainingModel.restartForPractice();
    }
}
