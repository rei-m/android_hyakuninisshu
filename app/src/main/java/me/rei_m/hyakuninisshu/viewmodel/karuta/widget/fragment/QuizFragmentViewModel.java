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

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.ToriFuda;
import me.rei_m.hyakuninisshu.domain.model.quiz.YomiFuda;
import me.rei_m.hyakuninisshu.model.KarutaQuizModel;
import me.rei_m.hyakuninisshu.presentation.karuta.enums.KarutaStyleFilter;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.GlideApp;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class QuizFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<String> quizCount = new ObservableField<>("");

    public final ObservableField<String> firstPhrase = new ObservableField<>("");

    public final ObservableField<String> secondPhrase = new ObservableField<>("");

    public final ObservableField<String> thirdPhrase = new ObservableField<>("");

    public final ObservableList<String> choiceFourthPhraseList = new ObservableArrayList<>();

    public final ObservableList<String> choiceFifthPhraseList = new ObservableArrayList<>();

    public final ObservableList<Boolean> isVisibleChoiceList = new ObservableArrayList<>();

    public final ObservableBoolean isVisibleResult = new ObservableBoolean(false);

    public final ObservableBoolean isCorrect = new ObservableBoolean(false);

    public final EventObservable<Unit> startDisplayAnimationEvent = EventObservable.create();

    public final EventObservable<Unit> stopDisplayAnimationEvent = EventObservable.create();

    public final EventObservable<Unit> onClickResultEvent = EventObservable.create();

    public final EventObservable<Unit> errorEvent = EventObservable.create();

    private final KarutaQuizModel karutaQuizModel;

    private KarutaStyleFilter kamiNoKuStyle;

    private KarutaStyleFilter shimoNoKuStyle;

    private KarutaQuizIdentifier karutaQuizId;

    private KarutaIdentifier correctKarutaId;

    private boolean existNextQuiz = false;

    public QuizFragmentViewModel(@NonNull KarutaQuizModel karutaQuizModel) {
        this.karutaQuizModel = karutaQuizModel;
        choiceFourthPhraseList.addAll(Arrays.asList("", "", "", ""));
        choiceFifthPhraseList.addAll(Arrays.asList("", "", "", ""));
        isVisibleChoiceList.addAll(Arrays.asList(true, true, true, true));
    }

    public KarutaQuizIdentifier karutaQuizId() {
        return karutaQuizId;
    }

    public KarutaIdentifier correctKarutaId() {
        return correctKarutaId;
    }

    public boolean existNextQuiz() {
        return existNextQuiz;
    }

    public void onCreate(@NonNull KarutaStyleFilter kamiNoKuStyle,
                         @NonNull KarutaStyleFilter shimoNoKuStyle) {
        this.kamiNoKuStyle = kamiNoKuStyle;
        this.shimoNoKuStyle = shimoNoKuStyle;
    }

    public void onReCreate(@NonNull KarutaQuizIdentifier quizId,
                           @NonNull KarutaStyleFilter kamiNoKuStyle,
                           @NonNull KarutaStyleFilter shimoNoKuStyle) {
        this.karutaQuizId = quizId;
        this.kamiNoKuStyle = kamiNoKuStyle;
        this.shimoNoKuStyle = shimoNoKuStyle;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaQuizModel.completeStartEvent.subscribe(karutaQuizContent -> {

            karutaQuizId = karutaQuizContent.quizId();

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

            if (!karutaQuizContent.isAnswered()) {
                startDisplayAnimationEvent.onNext(Unit.INSTANCE);
            }
        }), karutaQuizModel.completeAnswerEvent.subscribe(karutaQuizContent -> {

            stopDisplayAnimationEvent.onNext(Unit.INSTANCE);

            KarutaQuizResult result = karutaQuizContent.result();

            if (result == null) {
                errorEvent.onNext(Unit.INSTANCE);
                return;
            }

            List<Boolean> isVisibleChoiceList = Arrays.asList(false, false, false, false);
            isVisibleChoiceList.set(result.choiceNo().asIndex(), true);
            for (int i = 0; i < isVisibleChoiceList.size(); i++) {
                this.isVisibleChoiceList.set(i, isVisibleChoiceList.get(i));
            }
            this.isCorrect.set(result.isCorrect());
            this.isVisibleResult.set(true);

            this.correctKarutaId = result.correctKarutaId();
            this.existNextQuiz = karutaQuizContent.existNext();

        }), karutaQuizModel.errorEvent.subscribe(v -> errorEvent.onNext(Unit.INSTANCE)));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (karutaQuizId == null) {
            karutaQuizModel.start();
        } else {
            karutaQuizModel.restart(karutaQuizId);
        }
    }

    public void onClickChoice(int choiceNoValue) {
        karutaQuizModel.answer(karutaQuizId, ChoiceNo.forValue(choiceNoValue));
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
