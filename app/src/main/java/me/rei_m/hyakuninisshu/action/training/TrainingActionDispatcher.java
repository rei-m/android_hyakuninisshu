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

package me.rei_m.hyakuninisshu.action.training;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.action.Dispatcher;
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

public class TrainingActionDispatcher {

    private final Dispatcher dispatcher;
    private final KarutaRepository karutaRepository;
    private final KarutaQuizRepository karutaQuizRepository;
    private final KarutaExamRepository karutaExamRepository;

    @Inject
    public TrainingActionDispatcher(@NonNull Dispatcher dispatcher,
                                    @NonNull KarutaRepository karutaRepository,
                                    @NonNull KarutaQuizRepository karutaQuizRepository,
                                    @NonNull KarutaExamRepository karutaExamRepository) {
        this.dispatcher = dispatcher;
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaExamRepository = karutaExamRepository;
    }

    /**
     * 練習を開始する.
     *
     * @param fromKarutaId 練習範囲の開始歌ID
     * @param toKarutaId   練習範囲の終了歌ID
     * @param kimariji     決まり字
     * @param color        色
     */
    public void start(@NonNull KarutaIdentifier fromKarutaId,
                      @NonNull KarutaIdentifier toKarutaId,
                      @Nullable Kimariji kimariji,
                      @Nullable Color color) {
        start(karutaRepository.findIds(fromKarutaId, toKarutaId, color, kimariji));
    }

    /**
     * 力試しで過去に間違えた歌を練習対象にして練習を開始する.
     */
    public void startForExam() {
        start(karutaExamRepository.list().map(KarutaExams::totalWrongKarutaIds));
    }

    /**
     * 練習で間違えた歌を練習対象にして練習を再開する.
     */
    public void restartForPractice() {
        start(karutaQuizRepository.list().map(KarutaQuizzes::wrongKarutaIds));
    }

    /**
     * 練習結果を集計する.
     */
    public void aggregateResults() {
        karutaQuizRepository.list()
                .map(KarutaQuizzes::resultSummary)
                .map(TrainingResult::new)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trainingResult -> {
                    dispatcher.dispatch(new AggregateResultsAction(trainingResult));
                }, throwable -> {
                    dispatcher.dispatch(new AggregateResultsAction());
                });
    }

    private void start(Single<KarutaIds> karutaIdsSingle) {
        Single.zip(karutaRepository.list(), karutaIdsSingle, Karutas::createQuizSet)
                .flatMap(karutaQuizzes -> karutaQuizRepository.initialize(karutaQuizzes).andThen(Single.just(!karutaQuizzes.isEmpty())))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(hasQuiz -> {
                    if (hasQuiz) {
                        dispatcher.dispatch(new StartTrainingAction(false));
                    } else {
                        dispatcher.dispatch(new StartTrainingAction(true));
                    }
                },
                throwable -> dispatcher.dispatch(new StartTrainingAction(true))
        );
    }
}
