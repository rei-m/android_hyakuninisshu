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
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.action.exam.ExamActionDispatcher;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.QuizState;
import me.rei_m.hyakuninisshu.store.ExamStore;

public class ExamMasterActivityViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final ExamStore examStore;
        private final ExamActionDispatcher actionDispatcher;
        private QuizState quizState = QuizState.NOT_STARTED;
        private KarutaExamIdentifier finishedKarutaExamIdentifier;

        public Factory(@NonNull ExamStore examStore,
                       @NonNull ExamActionDispatcher actionDispatcher) {
            this.examStore = examStore;
            this.actionDispatcher = actionDispatcher;
        }

        public void setQuizState(@NonNull QuizState quizState) {
            this.quizState = quizState;
        }

        public void setFinishedKarutaExamIdentifier(@NonNull KarutaExamIdentifier finishedKarutaExamIdentifier) {
            this.finishedKarutaExamIdentifier = finishedKarutaExamIdentifier;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ExamMasterActivityViewModel.class)) {
                return (T) new ExamMasterActivityViewModel(examStore, actionDispatcher, quizState, finishedKarutaExamIdentifier);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableBoolean isVisibleAd = new ObservableBoolean(true);

    public final ObservableField<QuizState> quizState = new ObservableField<>();

    public final ObservableField<KarutaExamIdentifier> karutaExamIdentifier = new ObservableField<>();

    private final ExamActionDispatcher actionDispatcher;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final Observable.OnPropertyChangedCallback quizStateChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            switch (quizState.get()) {
                case STARTED:
                    isVisibleAd.set(false);
                    break;
                case FINISHED:
                    isVisibleAd.set(true);
                    break;
                case NOT_STARTED:
                    actionDispatcher.start();
                    break;
            }
        }
    };

    public ExamMasterActivityViewModel(@NonNull ExamStore examStore,
                                       @NonNull ExamActionDispatcher actionDispatcher,
                                       @NonNull QuizState quizState,
                                       @Nullable KarutaExamIdentifier karutaExamIdentifier) {
        this.actionDispatcher = actionDispatcher;
        this.quizState.addOnPropertyChangedCallback(quizStateChangedCallback);

        disposable.addAll(examStore.startedEvent.subscribe(v -> {
            this.quizState.set(QuizState.STARTED);
        }), examStore.finishedEvent.subscribe(finishedKarutaExamIdentifier -> {
            this.quizState.set(QuizState.FINISHED);
            this.karutaExamIdentifier.set(finishedKarutaExamIdentifier);
        }));
        this.quizState.set(quizState);
        this.karutaExamIdentifier.set(karutaExamIdentifier);
    }

    @Override
    protected void onCleared() {
        this.quizState.removeOnPropertyChangedCallback(quizStateChangedCallback);
        disposable.dispose();
        super.onCleared();
    }

    public void onClickGoToResult() {
        actionDispatcher.finish();
    }
}
