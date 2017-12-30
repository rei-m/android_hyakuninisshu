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

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.action.exam.ExamActionDispatcher;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizJudgement;
import me.rei_m.hyakuninisshu.store.ExamStore;
import me.rei_m.hyakuninisshu.util.Unit;

public class ExamResultFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final ExamStore examStore;
        private final ExamActionDispatcher actionDispatcher;
        private final KarutaExamIdentifier karutaExamId;

        public Factory(@NonNull ExamStore examStore,
                       @NonNull ExamActionDispatcher actionDispatcher,
                       @NonNull KarutaExamIdentifier karutaExamId) {
            this.examStore = examStore;
            this.actionDispatcher = actionDispatcher;
            this.karutaExamId = karutaExamId;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ExamResultFragmentViewModel.class)) {
                return (T) new ExamResultFragmentViewModel(examStore, actionDispatcher, karutaExamId);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableField<String> score = new ObservableField<>();

    public final ObservableFloat averageAnswerSec = new ObservableFloat();

    public final ObservableField<List<KarutaQuizJudgement>> karutaQuizJudgements = new ObservableField<>();

    private final PublishSubject<Unit> onClickBackMenuEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickBackMenuEvent = onClickBackMenuEventSubject;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public ExamResultFragmentViewModel(@NonNull ExamStore examStore,
                                       @NonNull ExamActionDispatcher actionDispatcher,
                                       @NonNull KarutaExamIdentifier karutaExamId) {
        disposable.addAll(examStore.karutaExam.subscribe(karutaExam -> {
            KarutaExamResult result = karutaExam.result();
            score.set(result.score());
            averageAnswerSec.set(result.averageAnswerSec());
            karutaQuizJudgements.set(result.judgements());
        }));

        actionDispatcher.fetch(karutaExamId);
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    @SuppressWarnings("unused")
    public void onClickBackMenu(View view) {
        onClickBackMenuEventSubject.onNext(Unit.INSTANCE);
    }
}
