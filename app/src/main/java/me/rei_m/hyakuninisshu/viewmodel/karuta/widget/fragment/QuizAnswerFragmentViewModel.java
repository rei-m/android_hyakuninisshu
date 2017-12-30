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
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.action.quiz.QuizActionDispatcher;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper;
import me.rei_m.hyakuninisshu.store.QuizStore;
import me.rei_m.hyakuninisshu.util.Unit;

public class QuizAnswerFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final QuizStore quizStore;
        private final QuizActionDispatcher actionDispatcher;
        private final KarutaQuizIdentifier quizId;

        public Factory(@NonNull QuizStore quizStore,
                       @NonNull QuizActionDispatcher actionDispatcher,
                       @NonNull KarutaQuizIdentifier quizId) {
            this.quizStore = quizStore;
            this.actionDispatcher = actionDispatcher;
            this.quizId = quizId;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(QuizAnswerFragmentViewModel.class)) {
                return (T) new QuizAnswerFragmentViewModel(quizStore, actionDispatcher, quizId);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableInt karutaNo = new ObservableInt();

    public final ObservableInt kimariji = new ObservableInt(0);

    public final ObservableField<String> creator = new ObservableField<>("");

    public final ObservableField<String> firstPhrase = new ObservableField<>("");

    public final ObservableField<String> secondPhrase = new ObservableField<>("");

    public final ObservableField<String> thirdPhrase = new ObservableField<>("");

    public final ObservableField<String> fourthPhrase = new ObservableField<>("");

    public final ObservableField<String> fifthPhrase = new ObservableField<>("");

    public final ObservableBoolean existNextQuiz = new ObservableBoolean(false);

    private final PublishSubject<KarutaIdentifier> onClickAnswerEventSubject = PublishSubject.create();
    public final Observable<KarutaIdentifier> onClickAnswerEvent = onClickAnswerEventSubject;

    private final PublishSubject<Unit> onClickNextQuizEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickNextQuizEvent = onClickNextQuizEventSubject;

    private final PublishSubject<Unit> onClickConfirmResultEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickConfirmResultEvent = onClickConfirmResultEventSubject;

    private final PublishSubject<Unit> errorEventSubject = PublishSubject.create();
    public final Observable<Unit> errorEvent = errorEventSubject;

    private KarutaIdentifier karutaId;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public QuizAnswerFragmentViewModel(@NonNull QuizStore quizStore,
                                       @NonNull QuizActionDispatcher actionDispatcher,
                                       @NonNull KarutaQuizIdentifier quizId) {
        disposable.addAll(quizStore.karutaQuizContent.subscribe(karutaQuizContent -> {
            Karuta karuta = karutaQuizContent.correct();
            karutaId = karuta.identifier();
            karutaNo.set(karuta.identifier().value());
            kimariji.set(karuta.kimariji().value());
            creator.set(karuta.creator());
            firstPhrase.set(padSpace(karuta.kamiNoKu().first().kanji(), 5));
            secondPhrase.set(karuta.kamiNoKu().second().kanji());
            thirdPhrase.set(karuta.kamiNoKu().third().kanji());
            fourthPhrase.set(padSpace(karuta.shimoNoKu().fourth().kanji(), 7));
            fifthPhrase.set(karuta.shimoNoKu().fifth().kanji());

            existNextQuiz.set(karutaQuizContent.existNext());
        }));
        actionDispatcher.fetch(quizId);
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    @SuppressWarnings("unused")
    public void onClickAnswer(View view) {
        if (karutaId != null) {
            onClickAnswerEventSubject.onNext(karutaId);
        }
    }

    @SuppressWarnings("unused")
    public void onClickNextQuiz(View view) {
        onClickNextQuizEventSubject.onNext(Unit.INSTANCE);
    }

    @SuppressWarnings("unused")
    public void onClickConfirmResult(View view) {
        onClickConfirmResultEventSubject.onNext(Unit.INSTANCE);
    }

    @BindingAdapter({"karutaNo", "kimariji"})
    public static void setKarutaNoAndKimariji(@NonNull TextView view,
                                              int karutaNo,
                                              int kimariji) {
        if (kimariji <= 0) {
            return;
        }

        Context context = view.getContext().getApplicationContext();
        String text = KarutaDisplayHelper.convertNumberToString(context, karutaNo) + " / " +
                KarutaDisplayHelper.convertKimarijiToString(context, kimariji);
        view.setText(text);
    }

    private static String padSpace(String text, int count) {

        int finallyCount = (count < text.length()) ? text.length() : count;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < finallyCount; i++) {
            builder.append("　");
        }
        return (text + builder.toString()).substring(0, finallyCount);
    }
}
