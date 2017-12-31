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
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.action.quiz.QuizActionDispatcher;
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.ToriFuda;
import me.rei_m.hyakuninisshu.domain.model.quiz.YomiFuda;
import me.rei_m.hyakuninisshu.presentation.helper.GlideApp;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.store.QuizStore;
import me.rei_m.hyakuninisshu.util.Unit;

public class QuizFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final QuizStore quizStore;
        private final QuizActionDispatcher actionDispatcher;
        private final KarutaStyleFilter kamiNoKuStyle;
        private final KarutaStyleFilter shimoNoKuStyle;

        public Factory(@NonNull QuizStore quizStore,
                       @NonNull QuizActionDispatcher actionDispatcher,
                       @NonNull KarutaStyleFilter kamiNoKuStyle,
                       @NonNull KarutaStyleFilter shimoNoKuStyle) {
            this.quizStore = quizStore;
            this.actionDispatcher = actionDispatcher;
            this.kamiNoKuStyle = kamiNoKuStyle;
            this.shimoNoKuStyle = shimoNoKuStyle;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(QuizFragmentViewModel.class)) {
                return (T) new QuizFragmentViewModel(quizStore, actionDispatcher, kamiNoKuStyle, shimoNoKuStyle);
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }

    public final ObservableField<KarutaQuizContent> karutaQuizContent = new ObservableField<>();

    public final ObservableField<String> quizCount = new ObservableField<>("");

    public final ObservableField<String> firstPhrase = new ObservableField<>("");

    public final ObservableField<String> secondPhrase = new ObservableField<>("");

    public final ObservableField<String> thirdPhrase = new ObservableField<>("");

    public final ObservableList<String> choiceFourthPhraseList = new ObservableArrayList<String>() {{
        addAll(Arrays.asList("", "", "", ""));
    }};

    public final ObservableList<String> choiceFifthPhraseList = new ObservableArrayList<String>() {{
        addAll(Arrays.asList("", "", "", ""));
    }};

    public final ObservableList<Boolean> isVisibleChoiceList = new ObservableArrayList<Boolean>() {{
        addAll(Arrays.asList(true, true, true, true));
    }};

    public final ObservableBoolean isVisibleResult = new ObservableBoolean(false);

    public final ObservableBoolean isCorrect = new ObservableBoolean(false);

    private final PublishSubject<Unit> startDisplayAnimationEventSubject = PublishSubject.create();
    public final Observable<Unit> startDisplayAnimationEvent = startDisplayAnimationEventSubject;

    private final PublishSubject<Unit> stopDisplayAnimationEventSubject = PublishSubject.create();
    public final Observable<Unit> stopDisplayAnimationEvent = stopDisplayAnimationEventSubject;

    private final PublishSubject<Unit> onClickResultEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickResultEvent = onClickResultEventSubject;

    private final PublishSubject<Unit> errorEventSubject = PublishSubject.create();
    public final Observable<Unit> errorEvent = errorEventSubject;

    private final QuizActionDispatcher actionDispatcher;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public QuizFragmentViewModel(@NonNull QuizStore quizStore,
                                 @NonNull QuizActionDispatcher actionDispatcher,
                                 @NonNull KarutaStyleFilter kamiNoKuStyle,
                                 @NonNull KarutaStyleFilter shimoNoKuStyle) {
        this.actionDispatcher = actionDispatcher;
        karutaQuizContent.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable observable, int i) {
                KarutaQuizResult result = karutaQuizContent.get().quiz().result();

                if (result == null) {
                    quizCount.set(karutaQuizContent.get().currentPosition());

                    YomiFuda yomiFuda = karutaQuizContent.get().yomiFuda(kamiNoKuStyle.value());
                    firstPhrase.set(yomiFuda.firstPhrase());
                    secondPhrase.set(yomiFuda.secondPhrase());
                    thirdPhrase.set(yomiFuda.thirdPhrase());

                    List<ToriFuda> toriFudas = karutaQuizContent.get().toriFudas(shimoNoKuStyle.value());
                    for (int choiceIndex = 0; choiceIndex < toriFudas.size(); choiceIndex++) {
                        ToriFuda toriFuda = toriFudas.get(choiceIndex);
                        choiceFourthPhraseList.set(choiceIndex, toriFuda.fourthPhrase());
                        choiceFifthPhraseList.set(choiceIndex, toriFuda.fifthPhrase());
                    }

                    startDisplayAnimationEventSubject.onNext(Unit.INSTANCE);
                } else {
                    stopDisplayAnimationEventSubject.onNext(Unit.INSTANCE);

                    for (int choiceIndex = 0; choiceIndex < isVisibleChoiceList.size(); choiceIndex++) {
                        isVisibleChoiceList.set(choiceIndex, false);
                    }
                    isVisibleChoiceList.set(result.choiceNo().asIndex(), true);
                    isCorrect.set(result.judgement().isCorrect());
                    isVisibleResult.set(true);
                }
            }
        });
        disposable.addAll(
                quizStore.karutaQuizContent.subscribe(karutaQuizContent::set),
                quizStore.error.subscribe(errorEventSubject::onNext)
        );
    }

    @Override
    protected void onCleared() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onCleared();
    }

    public void onResume() {
        if (karutaQuizContent.get() == null || karutaQuizContent.get().quiz().result() == null) {
            actionDispatcher.start(new Date());
        }
    }

    public void onPause() {
        if (karutaQuizContent.get() != null && karutaQuizContent.get().quiz().result() == null) {
            stopDisplayAnimationEventSubject.onNext(Unit.INSTANCE);
        }
    }

    public void onClickChoice(int choiceNoValue) {
        if (karutaQuizContent.get() == null) {
            return;
        }
        actionDispatcher.answer(karutaQuizContent.get().quiz().identifier(), ChoiceNo.forValue(choiceNoValue), new Date());
    }

    public void onClickResult() {
        disposable.dispose();
        onClickResultEventSubject.onNext(Unit.INSTANCE);
    }

    @BindingAdapter({"textForQuiz", "textPosition"})
    public static void setTextForQuiz(@NonNull TextView view,
                                      @NonNull String text,
                                      int textPosition) {
        if (text.length() < textPosition) {
            return;
        }
        view.setText(text.substring(textPosition - 1, textPosition));
    }

    @BindingAdapter({"isCorrect"})
    public static void setIsCorrect(@NonNull ImageView imageView,
                                    boolean isCorrect) {
        if (isCorrect) {
            GlideApp.with(imageView.getContext()).load(R.drawable.check_correct).dontAnimate().into(imageView);
        } else {
            GlideApp.with(imageView.getContext()).load(R.drawable.check_incorrect).dontAnimate().into(imageView);
        }
    }
}
