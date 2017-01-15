package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.github.gfx.android.orma.Inserter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizChoiceSchemaAdapter;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizSchemaAdapter;
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
                KarutaQuizSchema karutaQuizSchema = KarutaQuizSchemaAdapter.convert(karutaQuiz);
                karutaQuizSchemaInserter.execute(karutaQuizSchema);
                List<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaList = KarutaQuizChoiceSchemaAdapter.convert(karutaQuiz, karutaQuizSchema);
                karutaQuizChoiceSchemaInserter.executeAll(karutaQuizChoiceSchemaList);
            }
        });
    }

    @Override
    public Single<KarutaQuiz> pop() {

        if (karutaQuizCollection == null) {
            return Single.error(new IllegalStateException("quiz is not initialized"));
        }


        return Observable.fromIterable(karutaQuizCollection.values())
                .filter(karutaQuiz -> karutaQuiz.getResult() == null)
                .firstOrError();
    }

    @Override
    public Single<KarutaQuiz> resolve(KarutaQuizIdentifier identifier) {

        if (karutaQuizCollection == null) {
            return Single.error(new IllegalStateException("quiz is not initialized"));
        }

        return Single.just(karutaQuizCollection.get(identifier));
    }

    @Override
    public Completable store(KarutaQuiz karutaQuiz) {
        karutaQuizCollection.remove(karutaQuiz.getIdentifier());
        karutaQuizCollection.put(karutaQuiz.getIdentifier(), karutaQuiz);
        return Completable.complete();
    }

    @Override
    public Single<Boolean> existNextQuiz() {

        if (karutaQuizCollection == null) {
            return Single.error(new IllegalStateException("quiz is not initialized"));
        }

        return Observable.fromIterable(karutaQuizCollection.values())
                .filter(karutaQuiz -> karutaQuiz.getResult() == null)
                .count()
                .map(count -> (0 < count));
    }

    @Override
    public Single<List<KarutaQuiz>> asEntityList() {
        return Single.just(new ArrayList<>(karutaQuizCollection.values()));
    }

    @Override
    public Single<Pair<Integer, Integer>> countQuizByAnswered() {
        return Observable.fromIterable(karutaQuizCollection.values())
                .reduce(0, (answeredCount, karutaQuiz) -> (karutaQuiz.getResult() != null) ? answeredCount + 1 : answeredCount)
                .map(answeredCount -> new Pair<>(karutaQuizCollection.size(), answeredCount));
    }
}
