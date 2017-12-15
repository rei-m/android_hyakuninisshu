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
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.util.Unit;

public class ExamFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaExamModel karutaExamModel;

        public Factory(@NonNull KarutaExamModel karutaExamModel) {
            this.karutaExamModel = karutaExamModel;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ExamFragmentViewModel.class)) {
                return (T) new ExamFragmentViewModel(karutaExamModel);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableBoolean hasResult = new ObservableBoolean(false);

    public final ObservableField<String> score = new ObservableField<>("");

    public final ObservableFloat averageAnswerSec = new ObservableFloat();

    private final PublishSubject<Unit> onClickStartExamEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickStartExamEvent = onClickStartExamEventSubject;

    private final PublishSubject<Unit> onClickStartTrainingEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickStartTrainingEvent = onClickStartTrainingEventSubject;

    private CompositeDisposable disposable = null;

    public ExamFragmentViewModel(@NonNull KarutaExamModel karutaExamModel) {
        disposable = new CompositeDisposable();
        disposable.addAll(karutaExamModel.recentKarutaExam.subscribe(karutaExam -> {
            hasResult.set(true);
            score.set(karutaExam.result().score());
            averageAnswerSec.set(karutaExam.result().averageAnswerSec());
        }));

        karutaExamModel.fetchRecentKarutaExam();
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
    public void onClickStartExam(View view) {
        onClickStartExamEventSubject.onNext(Unit.INSTANCE);
    }

    @SuppressWarnings("unused")
    public void onClickStartTraining(View view) {
        onClickStartTrainingEventSubject.onNext(Unit.INSTANCE);
    }
}
