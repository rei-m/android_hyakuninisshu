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
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.domain.model.karuta.Color;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.Karutas;
import me.rei_m.hyakuninisshu.domain.model.karuta.Kimariji;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExams;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes;
import me.rei_m.hyakuninisshu.domain.model.quiz.TrainingResult;
import me.rei_m.hyakuninisshu.util.EventObservable;
import me.rei_m.hyakuninisshu.util.Unit;

@Singleton
public class KarutaTrainingModel {

    public final EventObservable<Unit> startedEvent = EventObservable.create();

    public final EventObservable<TrainingResult> result = EventObservable.create();

    public final EventObservable<Unit> notFoundErrorEvent = EventObservable.create();

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaExamRepository karutaExamRepository;

    @Inject
    public KarutaTrainingModel(@NonNull KarutaRepository karutaRepository,
                               @NonNull KarutaQuizRepository karutaQuizRepository,
                               @NonNull KarutaExamRepository karutaExamRepository) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaExamRepository = karutaExamRepository;
    }

    public void start(@NonNull KarutaIdentifier fromKarutaId,
                      @NonNull KarutaIdentifier toKarutaId,
                      @Nullable Kimariji kimariji,
                      @Nullable Color color) {
        start(karutaRepository.findIds(fromKarutaId, toKarutaId, color, kimariji));
    }

    public void startForExam() {
        start(karutaExamRepository.list().map(KarutaExams::totalWrongKarutaIds));
    }

    public void restartForPractice() {
        start(karutaQuizRepository.list().map(KarutaQuizzes::wrongKarutaIds));
    }

    public void aggregateResults() {
        karutaQuizRepository.list()
                .map(KarutaQuizzes::resultSummary)
                .map(TrainingResult::new)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result::onNext, throwable -> notFoundErrorEvent.onNext(Unit.INSTANCE));
    }

    private void start(Single<KarutaIds> karutaIdsSingle) {
        Single.zip(karutaRepository.list(), karutaIdsSingle, Karutas::createQuizSet)
                .flatMap(karutaQuizzes -> karutaQuizRepository.initialize(karutaQuizzes).andThen(Single.just(!karutaQuizzes.isEmpty())))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(hasQuiz -> {
                    if (hasQuiz) {
                        startedEvent.onNext(Unit.INSTANCE);
                    } else {
                        notFoundErrorEvent.onNext(Unit.INSTANCE);
                    }
                },
                throwable -> notFoundErrorEvent.onNext(Unit.INSTANCE)
        );
    }
}
