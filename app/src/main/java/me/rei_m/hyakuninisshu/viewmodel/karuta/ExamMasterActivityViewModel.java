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
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;

public class ExamMasterActivityViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaExamModel karutaExamModel;

        public Factory(@NonNull KarutaExamModel karutaExamModel) {
            this.karutaExamModel = karutaExamModel;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public ExamMasterActivityViewModel create(@NonNull Class modelClass) {
            return new ExamMasterActivityViewModel(karutaExamModel);
        }
    }

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(true);

    public final EventObservable<Unit> startedExamEvent = EventObservable.create();

    public final EventObservable<KarutaExamIdentifier> finishedExamEvent = EventObservable.create();

    public final EventObservable<Boolean> toggledAdEvent = EventObservable.create();

    private final KarutaExamModel karutaExamModel;

    private CompositeDisposable disposable = null;

    public ExamMasterActivityViewModel(@NonNull KarutaExamModel karutaExamModel) {
        this.karutaExamModel = karutaExamModel;

        disposable = new CompositeDisposable();
        disposable.addAll(karutaExamModel.startedEvent.subscribe(v -> {
            isVisibleAd.set(false);
            startedExamEvent.onNext(Unit.INSTANCE);
        }), karutaExamModel.finishedEvent.subscribe(karutaExamIdentifier -> {
            isVisibleAd.set(true);
            finishedExamEvent.onNext(karutaExamIdentifier);
        }));

        isVisibleAd.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toggledAdEvent.onNext(isVisibleAd.get());
            }
        });

        karutaExamModel.start();
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
    }

    public void onClickGoToResult() {
        karutaExamModel.aggregateResults();
    }
}
