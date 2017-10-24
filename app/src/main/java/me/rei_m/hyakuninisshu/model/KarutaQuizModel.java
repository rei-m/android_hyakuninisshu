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

package me.rei_m.hyakuninisshu.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karuta;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizCounter;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.event.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class KarutaQuizModel {

    public EventObservable<KarutaQuizContent> completeStartEvent = EventObservable.create();

    public EventObservable<KarutaQuizContent> completeAnswerEvent = EventObservable.create();

    public EventObservable<Unit> errorEvent = EventObservable.create();

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    @Inject
    public KarutaQuizModel(@NonNull KarutaRepository karutaRepository,
                           @NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
    }

    public void start() {
        karutaQuizRepository.first()
                .flatMap(karutaQuiz -> karutaQuizRepository.store(karutaQuiz.start(new Date())).toSingleDefault(karutaQuiz))
                .flatMap(this::createContent)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> completeStartEvent.onNext(v), e -> errorEvent.onNext(Unit.INSTANCE));
    }

    public void answer(@NonNull KarutaQuizIdentifier quizId, @NonNull ChoiceNo choiceNo) {
        karutaQuizRepository.findBy(quizId)
                .flatMap(karutaQuiz -> karutaQuizRepository.store(karutaQuiz.verify(choiceNo, new Date())).toSingleDefault(karutaQuiz))
                .flatMap(this::createContent)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> completeAnswerEvent.onNext(v), e -> {
                    e.printStackTrace();
                    errorEvent.onNext(Unit.INSTANCE);
                });
    }

    public void restart(@NonNull KarutaQuizIdentifier quizId) {
        karutaQuizRepository.findBy(quizId)
                .flatMap(this::createContent)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutaQuiz -> {
                    completeStartEvent.onNext(karutaQuiz);
                    if (karutaQuiz.result() != null) {
                        completeAnswerEvent.onNext(karutaQuiz);
                    }
                }, e -> errorEvent.onNext(Unit.INSTANCE));
    }

    private Single<KarutaQuizContent> createContent(@NonNull KarutaQuiz karutaQuiz) {
        Single<List<Karuta>> choiceSingle = Observable.fromIterable(karutaQuiz.choiceList())
                .flatMapSingle(karutaRepository::findBy)
                .toList();

        Single<Karuta> collectSingle = karutaRepository.findBy(karutaQuiz.correctId());

        Single<KarutaQuizCounter> countSingle = karutaQuizRepository.countQuizByAnswered();

        Single<Boolean> existNextQuizSingle = karutaQuizRepository.existNextQuiz();

        return Single.zip(choiceSingle, collectSingle, countSingle, existNextQuizSingle, (karutaList, karuta, count, existNextQuiz) ->
                new KarutaQuizContent(karutaQuiz, karuta, karutaList, count, existNextQuiz));
    }
}
