package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.github.gfx.android.orma.Inserter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizChoiceSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

public class KarutaQuizRepositoryImpl implements KarutaQuizRepository {

    private final OrmaDatabase orma;

    public KarutaQuizRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Completable initialize(List<KarutaQuiz> karutaQuizList) {
        return orma.transactionAsCompletable(() -> {
            KarutaQuizSchema.relation(orma).deleter().execute();
            KarutaQuizChoiceSchema.relation(orma).deleter().execute();

            Inserter<KarutaQuizSchema> karutaQuizSchemaInserter = KarutaQuizSchema.relation(orma).inserter();
            Inserter<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaInserter = KarutaQuizChoiceSchema.relation(orma).inserter();

            for (KarutaQuiz karutaQuiz : karutaQuizList) {
                KarutaQuizSchema karutaQuizSchema = karutaQuiz.toSchema();
                karutaQuizSchema.id = karutaQuizSchemaInserter.execute(karutaQuizSchema);
                karutaQuizChoiceSchemaInserter.executeAll(karutaQuiz.toSchema(karutaQuizSchema));
            }
        });
    }

    @Override
    public Single<KarutaQuiz> pop() {
        return KarutaQuizSchema.relation(orma)
                .where("answerTime = ?", 0)
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map(karutaQuizSchema -> {
                    List<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaList = new ArrayList<>();
                    karutaQuizSchema.getChoices(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe(karutaQuizChoiceSchemaList::add);
                    return KarutaQuiz.create(karutaQuizSchema, karutaQuizChoiceSchemaList);
                });
    }

    @Override
    public Single<KarutaQuiz> resolve(KarutaQuizIdentifier identifier) {
        return KarutaQuizSchema.relation(orma)
                .quizIdEq(identifier.getValue())
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map(karutaQuizSchema -> {
                    List<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaList = new ArrayList<>();
                    karutaQuizSchema.getChoices(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe(karutaQuizChoiceSchemaList::add);
                    return KarutaQuiz.create(karutaQuizSchema, karutaQuizChoiceSchemaList);
                });
    }

    @Override
    public Completable store(KarutaQuiz karutaQuiz) {
        return orma.transactionAsCompletable(() -> {
            KarutaQuizSchema karutaQuizSchema = karutaQuiz.toSchema();
            KarutaQuizSchema.relation(orma).quizIdEq(karutaQuizSchema.quizId).updater()
                    .startDate(karutaQuizSchema.startDate)
                    .isCollect(karutaQuizSchema.isCollect)
                    .answerTime(karutaQuizSchema.answerTime)
                    .execute();
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
    public Single<List<KarutaQuiz>> asEntityList() {
        return KarutaQuizSchema.relation(orma)
                .selector()
                .executeAsObservable()
                .map(karutaQuizSchema -> {
                    List<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaList = new ArrayList<>();
                    karutaQuizSchema.getChoices(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe(karutaQuizChoiceSchemaList::add);
                    return KarutaQuiz.create(karutaQuizSchema, karutaQuizChoiceSchemaList);
                }).toList();
    }

    @Override
    public Single<Pair<Integer, Integer>> countQuizByAnswered() {
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
                new Pair<>(totalCount.intValue(), answeredCount.intValue()));
    }
}
