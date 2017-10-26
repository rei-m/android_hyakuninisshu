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

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class KarutaExamModel {

    public final EventObservable<Unit> completeStartEvent = EventObservable.create();

    public final EventObservable<KarutaExamIdentifier> completeAggregateResultsEvent = EventObservable.create();

    public final EventObservable<KarutaExamResult> completeFetchResultEvent = EventObservable.create();

    public final EventObservable<Unit> notFoundResultEvent = EventObservable.create();

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaExamRepository karutaExamRepository;

    @Inject
    public KarutaExamModel(@NonNull KarutaRepository karutaRepository,
                           @NonNull KarutaQuizRepository karutaQuizRepository,
                           @NonNull KarutaExamRepository karutaExamRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaExamRepository = karutaExamRepository;
    }

    public void start() {
        Single.zip(karutaRepository.list(), karutaRepository.findIds(), Karutas::createQuizSet)
                .flatMapCompletable(karutaQuizRepository::initialize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> completeStartEvent.onNext(Unit.INSTANCE));
    }

    public void aggregateResults() {
        karutaQuizRepository.list()
                .map(karutaQuizzes -> new KarutaExamResult(karutaQuizzes.resultSummary(), karutaQuizzes.wrongKarutaIds()))
                .flatMap(karutaExamResult -> karutaExamRepository.storeResult(karutaExamResult, new Date()))
                .flatMap(karutaExamIdentifier -> karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT).andThen(Single.just(karutaExamIdentifier)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeAggregateResultsEvent::onNext);
    }

    public void fetchResult(KarutaExamIdentifier karutaExamIdentifier) {
        karutaExamRepository.findBy(karutaExamIdentifier)
                .map(KarutaExam::result)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeFetchResultEvent::onNext);
    }

    public void fetchRecentResult() {
        karutaExamRepository.list()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutaExams -> {
                    KarutaExam recentKarutaExam = karutaExams.recent();
                    if (recentKarutaExam == null) {
                        notFoundResultEvent.onNext(Unit.INSTANCE);
                    } else {
                        completeFetchResultEvent.onNext(recentKarutaExam.result());
                    }
                });
    }
}
