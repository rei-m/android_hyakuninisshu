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

package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.NonNull;

import com.github.gfx.android.orma.Inserter;
import com.github.gfx.android.orma.SingleAssociation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExams;

public class KarutaExamRepositoryImpl implements KarutaExamRepository {

    private final OrmaDatabase orma;

    public KarutaExamRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Single<KarutaExamIdentifier> storeResult(@NonNull KarutaExamResult karutaExamResult,
                                                    @NonNull Date tookExamDate) {

        KarutaExamSchema karutaExamSchema = new KarutaExamSchema();

        orma.transactionSync(() -> {
            karutaExamSchema.tookExamDate = tookExamDate;
            karutaExamSchema.totalQuizCount = karutaExamResult.quizCount();
            karutaExamSchema.averageAnswerTime = karutaExamResult.averageAnswerTime();
            karutaExamSchema.id = KarutaExamSchema.relation(orma).inserter().execute(karutaExamSchema);

            Inserter<ExamWrongKarutaSchema> examWrongKarutaSchemaInserter = ExamWrongKarutaSchema.relation(orma).inserter();
            Observable.fromIterable(karutaExamResult.wrongKarutaIds().asList()).subscribe(identifier -> {
                ExamWrongKarutaSchema examWrongKarutaSchema = new ExamWrongKarutaSchema();
                examWrongKarutaSchema.examSchema = SingleAssociation.just(karutaExamSchema);
                examWrongKarutaSchema.karutaId = identifier.value();
                examWrongKarutaSchemaInserter.execute(examWrongKarutaSchema);
            });
        });

        return Single.just(new KarutaExamIdentifier(karutaExamSchema.id));
    }

    @Override
    public Completable adjustHistory(int historySize) {
        Observable<KarutaExamSchema> karutaExamSchemaObservable = KarutaExamSchema.relation(orma)
                .selector()
                .orderByIdAsc()
                .executeAsObservable();

        long examCount = karutaExamSchemaObservable.count().blockingGet();

        if (historySize < examCount) {
            return orma.transactionAsCompletable(() -> {
                KarutaExamSchema_Deleter deleter = KarutaExamSchema.relation(orma).deleter();
                karutaExamSchemaObservable
                        .take(examCount - historySize)
                        .subscribe(karutaExamSchema -> deleter.idEq(karutaExamSchema.id).execute());
            });
        } else {
            return Completable.complete();
        }
    }

    @Override
    public Single<KarutaExam> findBy(@NonNull KarutaExamIdentifier identifier) {
        return KarutaExamSchema.relation(orma)
                .idEq(identifier.value())
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map(examSchema -> {
                    List<ExamWrongKarutaSchema> examWrongKarutaSchemaList = new ArrayList<>();
                    examSchema.getWrongKarutas(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe(examWrongKarutaSchemaList::add);
                    return KarutaExamFactory.create(examSchema, examWrongKarutaSchemaList);
                });
    }

    @Override
    public Single<KarutaExams> list() {
        return KarutaExamSchema.relation(orma)
                .selector()
                .orderByIdDesc()
                .executeAsObservable()
                .map(examSchema -> {
                    List<ExamWrongKarutaSchema> examWrongKarutaSchemaList = new ArrayList<>();
                    examSchema.getWrongKarutas(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe(examWrongKarutaSchemaList::add);
                    return KarutaExamFactory.create(examSchema, examWrongKarutaSchemaList);
                }).toList()
                .map(KarutaExams::new);
    }
}
