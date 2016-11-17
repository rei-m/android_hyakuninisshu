package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;

public class KarutaQuizRepositoryImpl implements KarutaQuizRepository {

    private Map<KarutaQuizIdentifier, KarutaQuiz> karutaQuizCollection;

    @Override
    public Completable initialize(List<KarutaQuiz> karutaQuizList) {
        this.karutaQuizCollection = new HashMap<>();
        for (KarutaQuiz karutaQuiz : karutaQuizList) {
            this.karutaQuizCollection.put(karutaQuiz.getIdentifier(), karutaQuiz);
        }
        return Completable.complete();
    }

    @Override
    public Maybe<KarutaQuiz> pop() {
        return Observable.fromIterable(karutaQuizCollection.values())
                .filter(karutaQuiz -> karutaQuiz.getResult() == null)
                .firstElement();
    }

    @Override
    public Maybe<KarutaQuiz> resolve(KarutaQuizIdentifier identifier) {
        return Maybe.just(karutaQuizCollection.get(identifier));
    }

    @Override
    public Completable store(KarutaQuiz karutaQuiz) {
        karutaQuizCollection.remove(karutaQuiz.getIdentifier());
        karutaQuizCollection.put(karutaQuiz.getIdentifier(), karutaQuiz);
        return Completable.complete();
    }

    @Override
    public Single<Boolean> existNextQuiz() {
        return Observable.fromIterable(karutaQuizCollection.values())
                .filter(karutaQuiz -> karutaQuiz.getResult() == null)
                .count()
                .map(count -> (0 < count));
    }

    @Override
    public Single<List<KarutaQuiz>> asEntityList() {
        return Single.just(new ArrayList<>(karutaQuizCollection.values()));
    }
}
