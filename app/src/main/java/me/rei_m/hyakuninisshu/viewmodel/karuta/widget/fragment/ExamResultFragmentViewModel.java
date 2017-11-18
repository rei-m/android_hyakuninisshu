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
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizJudgement;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;

public class ExamResultFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaExamModel karutaExamModel;

        private final KarutaExamIdentifier karutaExamId;

        public Factory(@NonNull KarutaExamModel karutaExamModel,
                       @NonNull KarutaExamIdentifier karutaExamId) {
            this.karutaExamModel = karutaExamModel;
            this.karutaExamId = karutaExamId;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public ExamResultFragmentViewModel create(@NonNull Class modelClass) {
            return new ExamResultFragmentViewModel(karutaExamModel, karutaExamId);
        }
    }

    public final ObservableField<String> score = new ObservableField<>();

    public final ObservableFloat averageAnswerTime = new ObservableFloat();

    public final ObservableField<List<KarutaQuizJudgement>> karutaQuizJudgements = new ObservableField<>();

    public final EventObservable<Unit> onClickBackMenuEvent = EventObservable.create();

    private CompositeDisposable disposable = null;

    public ExamResultFragmentViewModel(@NonNull KarutaExamModel karutaExamModel,
                                       @NonNull KarutaExamIdentifier karutaExamId) {
        disposable = new CompositeDisposable();
        disposable.addAll(karutaExamModel.karutaExam.subscribe(karutaExam -> {
            KarutaExamResult result = karutaExam.result();
            score.set(result.score());
            averageAnswerTime.set(result.averageAnswerTime());
            karutaQuizJudgements.set(result.judgements());
        }));

        karutaExamModel.fetchKarutaExam(karutaExamId);
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
    public void onClickBackMenu(View view) {
        onClickBackMenuEvent.onNext(Unit.INSTANCE);
    }
}
