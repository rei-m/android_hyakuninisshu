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

    public final EventObservable<Unit> startExamEvent = EventObservable.create();

    public final EventObservable<KarutaExamIdentifier> aggregateExamResultsEvent = EventObservable.create();

    public final EventObservable<Boolean> toggleAdEvent = EventObservable.create();

    private boolean isVisibleAd = false;

    private final KarutaExamModel karutaExamModel;

    private CompositeDisposable disposable = null;

    public ExamMasterActivityViewModel(@NonNull KarutaExamModel karutaExamModel) {
        this.karutaExamModel = karutaExamModel;

        disposable = new CompositeDisposable();
        disposable.addAll(karutaExamModel.completeStartEvent.subscribe(v -> {
            isVisibleAd = false;
            toggleAdEvent.onNext(false);
            startExamEvent.onNext(Unit.INSTANCE);
        }), karutaExamModel.finishedExamId.subscribe(karutaExamIdentifier -> {
            isVisibleAd = true;
            toggleAdEvent.onNext(true);
            aggregateExamResultsEvent.onNext(karutaExamIdentifier);
        }));
        karutaExamModel.start();
    }

    public boolean isVisibleAd() {
        return isVisibleAd;
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
