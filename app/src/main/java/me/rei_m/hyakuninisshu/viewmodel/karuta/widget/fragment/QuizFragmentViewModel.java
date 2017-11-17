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
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.ToriFuda;
import me.rei_m.hyakuninisshu.domain.model.quiz.YomiFuda;
import me.rei_m.hyakuninisshu.model.KarutaQuizModel;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.GlideApp;
import me.rei_m.hyakuninisshu.util.Unit;

public class QuizFragmentViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private final KarutaQuizModel karutaQuizModel;

        private final KarutaStyleFilter kamiNoKuStyle;

        private final KarutaStyleFilter shimoNoKuStyle;

        public Factory(@NonNull KarutaQuizModel karutaQuizModel,
                       @NonNull KarutaStyleFilter kamiNoKuStyle,
                       @NonNull KarutaStyleFilter shimoNoKuStyle) {
            this.karutaQuizModel = karutaQuizModel;
            this.kamiNoKuStyle = kamiNoKuStyle;
            this.shimoNoKuStyle = shimoNoKuStyle;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public QuizFragmentViewModel create(@NonNull Class modelClass) {
            return new QuizFragmentViewModel(karutaQuizModel, kamiNoKuStyle, shimoNoKuStyle);
        }
    }

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

    public final EventObservable<Unit> startDisplayAnimationEvent = EventObservable.create();

    public final EventObservable<Unit> stopDisplayAnimationEvent = EventObservable.create();

    public final EventObservable<Unit> onClickResultEvent = EventObservable.create();

    public final EventObservable<Unit> errorEvent = EventObservable.create();

    private final KarutaQuizModel karutaQuizModel;

    private CompositeDisposable disposable = null;

    private KarutaQuizContent karutaQuizContent = null;

    public QuizFragmentViewModel(@NonNull KarutaQuizModel karutaQuizModel,
                                 @NonNull KarutaStyleFilter kamiNoKuStyle,
                                 @NonNull KarutaStyleFilter shimoNoKuStyle) {
        this.karutaQuizModel = karutaQuizModel;

        disposable = new CompositeDisposable();
        disposable.addAll(karutaQuizModel.karutaQuizContent.subscribe(karutaQuizContent -> {

            this.karutaQuizContent = karutaQuizContent;

            KarutaQuizResult result = karutaQuizContent.quiz().result();

            if (result == null) {
                quizCount.set(karutaQuizContent.currentPosition());

                YomiFuda yomiFuda = karutaQuizContent.yomiFuda(kamiNoKuStyle.value());

                firstPhrase.set(yomiFuda.firstPhrase());
                secondPhrase.set(yomiFuda.secondPhrase());
                thirdPhrase.set(yomiFuda.thirdPhrase());

                List<ToriFuda> toriFudas = karutaQuizContent.toriFudas(shimoNoKuStyle.value());

                for (int i = 0; i < toriFudas.size(); i++) {
                    ToriFuda toriFuda = toriFudas.get(i);
                    choiceFourthPhraseList.set(i, toriFuda.fourthPhrase());
                    choiceFifthPhraseList.set(i, toriFuda.fifthPhrase());
                }

                startDisplayAnimationEvent.onNext(Unit.INSTANCE);
            } else {
                stopDisplayAnimationEvent.onNext(Unit.INSTANCE);

                List<Boolean> isVisibleChoiceList = Arrays.asList(false, false, false, false);
                isVisibleChoiceList.set(result.choiceNo().asIndex(), true);
                for (int i = 0; i < isVisibleChoiceList.size(); i++) {
                    this.isVisibleChoiceList.set(i, isVisibleChoiceList.get(i));
                }
                this.isCorrect.set(result.judgement().isCorrect());
                this.isVisibleResult.set(true);
            }
        }), karutaQuizModel.errorEvent.subscribe(v -> errorEvent.onNext(Unit.INSTANCE)));
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onCleared();
    }

    @Nullable
    public KarutaQuizContent karutaQuizContent() {
        return karutaQuizContent;
    }

    public void onResume() {
        if (karutaQuizContent == null) {
            karutaQuizModel.start();
        } else {
            if (karutaQuizContent.quiz().result() == null) {
                karutaQuizModel.start();
            }
        }
    }

    public void onPause() {
        if (karutaQuizContent != null && karutaQuizContent.quiz().result() == null) {
            stopDisplayAnimationEvent.onNext(Unit.INSTANCE);
        }
    }

    public void onClickChoice(int choiceNoValue) {
        if (karutaQuizContent == null) {
            return;
        }
        karutaQuizModel.answer(karutaQuizContent.quiz().identifier(), ChoiceNo.forValue(choiceNoValue));
    }

    public void onClickResult() {
        onClickResultEvent.onNext(Unit.INSTANCE);
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
