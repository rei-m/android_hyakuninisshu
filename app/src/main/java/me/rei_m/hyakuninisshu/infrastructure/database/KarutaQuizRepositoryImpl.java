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
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.ChoiceNo;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizCounter;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizzes;

public class KarutaQuizRepositoryImpl implements KarutaQuizRepository {

    private final OrmaDatabase orma;

    public KarutaQuizRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Completable initialize(@NonNull KarutaQuizzes karutaQuizzes) {
        return orma.transactionAsCompletable(() -> {

            KarutaQuizSchema.relation(orma).deleter().execute();
            KarutaQuizChoiceSchema.relation(orma).deleter().execute();

            Inserter<KarutaQuizSchema> karutaQuizSchemaInserter = KarutaQuizSchema.relation(orma).inserter();
            Inserter<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaInserter = KarutaQuizChoiceSchema.relation(orma).inserter();

            for (KarutaQuiz karutaQuiz : karutaQuizzes.asList()) {
                KarutaQuizSchema karutaQuizSchema = new KarutaQuizSchema();
                karutaQuizSchema.quizId = karutaQuiz.identifier().value();
                karutaQuizSchema.collectId = karutaQuiz.correctId().value();
                karutaQuizSchema.id = karutaQuizSchemaInserter.execute(karutaQuizSchema);
                List<KarutaIdentifier> choiceList = karutaQuiz.choiceList();
                for (KarutaIdentifier karutaIdentifier : choiceList) {
                    KarutaQuizChoiceSchema karutaQuizChoiceSchema = new KarutaQuizChoiceSchema();
                    karutaQuizChoiceSchema.karutaQuizSchema = SingleAssociation.just(karutaQuizSchema);
                    karutaQuizChoiceSchema.karutaId = karutaIdentifier.value();
                    karutaQuizChoiceSchema.orderNo = choiceList.indexOf(karutaIdentifier);
                    karutaQuizChoiceSchemaInserter.execute(karutaQuizChoiceSchema);
                }
            }
        });
    }

    @Override
    public Single<KarutaQuiz> first() {
        return KarutaQuizSchema.relation(orma)
                .where("answerTime = ?", 0)
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map(karutaQuizSchema -> {
                    List<KarutaIdentifier> karutaIdentifierList = new ArrayList<>();
                    karutaQuizSchema.getChoices(orma)
                            .selector()
                            .executeAsObservable()
                            .map(karutaQuizChoiceSchema -> new KarutaIdentifier(karutaQuizChoiceSchema.karutaId))
                            .subscribe(karutaIdentifierList::add);
                    return new KarutaQuiz(new KarutaQuizIdentifier(karutaQuizSchema.quizId),
                            karutaIdentifierList,
                            new KarutaIdentifier(karutaQuizSchema.collectId));
                });
    }

    @Override
    public Single<KarutaQuiz> findBy(@NonNull KarutaQuizIdentifier identifier) {
        return KarutaQuizSchema.relation(orma)
                .quizIdEq(identifier.value())
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map(funcConvertEntity);
    }

    @Override
    public Completable store(@NonNull KarutaQuiz karutaQuiz) {
        return orma.transactionAsCompletable(() -> {
            KarutaQuizSchema_Updater updater = KarutaQuizSchema.relation(orma)
                    .quizIdEq(karutaQuiz.identifier().value())
                    .updater();

            updater.startDate(karutaQuiz.startDate());
            if (karutaQuiz.result() != null) {
                updater.isCollect(karutaQuiz.result().isCorrect())
                        .choiceNo(karutaQuiz.result().choiceNo().value())
                        .answerTime(karutaQuiz.result().answerTime());
            }
            updater.execute();
        });
    }

    @Override
    public Single<Boolean> existNextQuiz() {
        return KarutaQuizSchema.relation(orma)
                .where("answerTime = ?", 0)
                .selector()
                .executeAsObservable().count()
                .map(count -> 0 < count);
    }

    @Override
    public Single<KarutaQuizzes> list() {
        return KarutaQuizSchema.relation(orma)
                .selector()
                .executeAsObservable()
                .map(funcConvertEntity)
                .toList()
                .map(KarutaQuizzes::new);
    }

    @Override
    public Single<KarutaQuizCounter> countQuizByAnswered() {
        Single<Long> totalCountSingle = KarutaQuizSchema.relation(orma)
                .selector()
                .executeAsObservable()
                .count();

        Single<Long> answeredCountSingle = KarutaQuizSchema.relation(orma)
                .where("answerTime > ?", 0)
                .selector()
                .executeAsObservable()
                .count();

        return Single.zip(totalCountSingle, answeredCountSingle, (totalCount, answeredCount) ->
                new KarutaQuizCounter(totalCount.intValue(), answeredCount.intValue()));
    }

    private Function<KarutaQuizSchema, KarutaQuiz> funcConvertEntity = new Function<KarutaQuizSchema, KarutaQuiz>() {
        @Override
        public KarutaQuiz apply(KarutaQuizSchema karutaQuizSchema) {
            List<KarutaIdentifier> karutaIdentifierList = new ArrayList<>();
            karutaQuizSchema.getChoices(orma)
                    .selector()
                    .executeAsObservable()
                    .map(karutaQuizChoiceSchema -> new KarutaIdentifier(karutaQuizChoiceSchema.karutaId))
                    .subscribe(karutaIdentifierList::add);

            if (karutaQuizSchema.startDate == null) {
                return new KarutaQuiz(new KarutaQuizIdentifier(karutaQuizSchema.quizId),
                        karutaIdentifierList,
                        new KarutaIdentifier(karutaQuizSchema.collectId));
            } else {
                if (karutaQuizSchema.answerTime > 0) {
                    return new KarutaQuiz(new KarutaQuizIdentifier(karutaQuizSchema.quizId),
                            karutaIdentifierList,
                            new KarutaIdentifier(karutaQuizSchema.collectId),
                            karutaQuizSchema.startDate,
                            karutaQuizSchema.answerTime,
                            ChoiceNo.forValue(karutaQuizSchema.choiceNo),
                            karutaQuizSchema.isCollect);
                } else {
                    return new KarutaQuiz(new KarutaQuizIdentifier(karutaQuizSchema.quizId),
                            karutaIdentifierList,
                            new KarutaIdentifier(karutaQuizSchema.collectId),
                            karutaQuizSchema.startDate);
                }
            }
        }
    };
}
