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
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
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
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ExamMasterActivityViewModel.class)) {
                return (T) new ExamMasterActivityViewModel(karutaExamModel);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(true);

    private final PublishSubject<Unit> startedExamEventSubject = PublishSubject.create();
    public final Observable<Unit> startedExamEvent = startedExamEventSubject;

    private final PublishSubject<KarutaExamIdentifier> finishedExamEventSubject = PublishSubject.create();
    public final Observable<KarutaExamIdentifier> finishedExamEvent = finishedExamEventSubject;

    private final PublishSubject<Boolean> toggledAdEventSubject = PublishSubject.create();
    public final Observable<Boolean> toggledAdEvent = toggledAdEventSubject;

    private final KarutaExamModel karutaExamModel;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public ExamMasterActivityViewModel(@NonNull KarutaExamModel karutaExamModel) {
        this.karutaExamModel = karutaExamModel;

        disposable.addAll(karutaExamModel.startedEvent.subscribe(v -> {
            isVisibleAd.set(false);
            startedExamEventSubject.onNext(Unit.INSTANCE);
        }), karutaExamModel.finishedEvent.subscribe(karutaExamIdentifier -> {
            isVisibleAd.set(true);
            finishedExamEventSubject.onNext(karutaExamIdentifier);
        }));

        isVisibleAd.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable observable, int i) {
                toggledAdEventSubject.onNext(isVisibleAd.get());
            }
        });

        karutaExamModel.start();
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public void onClickGoToResult() {
        karutaExamModel.finish();
    }
}
