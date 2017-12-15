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

package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.util.Unit;

public class QuizResultFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaTrainingModel karutaTrainingModel;

        public Factory(@NonNull KarutaTrainingModel karutaTrainingModel) {
            this.karutaTrainingModel = karutaTrainingModel;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(QuizResultFragmentViewModel.class)) {
                return (T) new QuizResultFragmentViewModel(karutaTrainingModel);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableField<String> score = new ObservableField<>("");

    public final ObservableFloat averageAnswerSec = new ObservableFloat();

    public final ObservableBoolean canRestartTraining = new ObservableBoolean();

    private final PublishSubject<Unit> onClickRestartEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickRestartEvent = onClickRestartEventSubject;

    private final PublishSubject<Unit> onClickBackMenuEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickBackMenuEvent = onClickBackMenuEventSubject;

    private CompositeDisposable disposable = null;

    public QuizResultFragmentViewModel(@NonNull KarutaTrainingModel karutaTrainingModel) {
        disposable = new CompositeDisposable();
        disposable.addAll(karutaTrainingModel.result.subscribe(trainingResult -> {
            score.set(trainingResult.correctCount() + "/" + trainingResult.quizCount());
            averageAnswerSec.set(trainingResult.averageAnswerSec());
            canRestartTraining.set(trainingResult.canRestartTraining());
        }));
        karutaTrainingModel.aggregateResults();
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
    }

    @SuppressWarnings("unused")
    public void onClickPracticeWrongKarutas(View view) {
        onClickRestartEventSubject.onNext(Unit.INSTANCE);
    }

    @SuppressWarnings("unused")
    public void onClickBackMenu(View view) {
        onClickBackMenuEventSubject.onNext(Unit.INSTANCE);
    }
}
