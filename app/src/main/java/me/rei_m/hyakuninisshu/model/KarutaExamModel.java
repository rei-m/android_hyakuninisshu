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

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.util.Unit;

/**
 * 力試しモデル.
 */
@Singleton
public class KarutaExamModel {

    private final PublishSubject<KarutaExam> karutaExamSubject = PublishSubject.create();
    public final Observable<KarutaExam> karutaExam = karutaExamSubject;

    private final PublishSubject<KarutaExam> recentKarutaExamSubject = PublishSubject.create();
    public final Observable<KarutaExam> recentKarutaExam = recentKarutaExamSubject;

    private final PublishSubject<Unit> startedEventSubject = PublishSubject.create();
    public final Observable<Unit> startedEvent = startedEventSubject;

    private final PublishSubject<KarutaExamIdentifier> finishedEventSubject = PublishSubject.create();
    public final Observable<KarutaExamIdentifier> finishedEvent = finishedEventSubject;

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

    /**
     * 力試しを取得する.
     *
     * @param karutaExamIdentifier 力試しID
     */
    public void fetchKarutaExam(@NonNull KarutaExamIdentifier karutaExamIdentifier) {
        karutaExamRepository.findBy(karutaExamIdentifier)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.karutaExamSubject::onNext);
    }

    /**
     * 最新の力試しを取得する.
     */
    public void fetchRecentKarutaExam() {
        karutaExamRepository.list()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutaExams -> {
                    KarutaExam recent = karutaExams.recent();
                    if (recent != null) {
                        recentKarutaExamSubject.onNext(recent);
                    }
                });
    }

    /**
     * 力試しを開始する.
     */
    public void start() {
        Single.zip(karutaRepository.list(), karutaRepository.findIds(), Karutas::createQuizSet)
                .flatMapCompletable(karutaQuizRepository::initialize)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> startedEventSubject.onNext(Unit.INSTANCE));
    }

    /**
     * 力試しを終了して結果を登録する.
     */
    public void finish() {
        // TODO: 未解答の問題があった場合エラー
        karutaQuizRepository.list()
                .map(karutaQuizzes -> new KarutaExamResult(karutaQuizzes.resultSummary(), karutaQuizzes.wrongKarutaIds()))
                .flatMap(karutaExamResult -> karutaExamRepository.storeResult(karutaExamResult, new Date()))
                .flatMap(karutaExamIdentifier -> karutaExamRepository.adjustHistory(KarutaExam.MAX_HISTORY_COUNT).andThen(karutaExamRepository.findBy(karutaExamIdentifier)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(karutaExam -> {
                    this.finishedEventSubject.onNext(karutaExam.identifier());
                    this.recentKarutaExamSubject.onNext(karutaExam);
                });
    }
}
