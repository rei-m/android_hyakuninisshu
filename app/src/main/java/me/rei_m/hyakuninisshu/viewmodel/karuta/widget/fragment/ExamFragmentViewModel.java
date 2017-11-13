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

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.util.EventObservable;
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
        public ExamFragmentViewModel create(@NonNull Class modelClass) {
            return new ExamFragmentViewModel(karutaExamModel);
        }
    }

    public final ObservableBoolean hasResult = new ObservableBoolean(false);

    public final ObservableField<String> score = new ObservableField<>("");

    public final ObservableFloat averageAnswerTime = new ObservableFloat();

    public final EventObservable<Unit> onClickStartExamEvent = EventObservable.create();

    public final EventObservable<Unit> onClickStartTrainingEvent = EventObservable.create();

    private CompositeDisposable disposable = null;

    public ExamFragmentViewModel(@NonNull KarutaExamModel karutaExamModel) {
        disposable = new CompositeDisposable();
        disposable.addAll(karutaExamModel.karutaExams.subscribe(karutaExams -> {
            KarutaExam recentKarutaExam = karutaExams.recent();
            if (recentKarutaExam == null) {
                hasResult.set(false);
            } else {
                hasResult.set(true);
                score.set(recentKarutaExam.result().score());
                averageAnswerTime.set(recentKarutaExam.result().averageAnswerTime());
            }
        }));

        karutaExamModel.getKarutaExams();
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
        onClickStartExamEvent.onNext(Unit.INSTANCE);
    }

    @SuppressWarnings("unused")
    public void onClickStartTraining(View view) {
        onClickStartTrainingEvent.onNext(Unit.INSTANCE);
    }
}
