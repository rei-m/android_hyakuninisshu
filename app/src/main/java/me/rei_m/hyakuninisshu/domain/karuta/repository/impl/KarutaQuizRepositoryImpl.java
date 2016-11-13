package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;

public class KarutaQuizRepositoryImpl implements KarutaQuizRepository {

    private Map<KarutaQuizIdentifier, KarutaQuiz> karutaQuizCollection;

    @Override
    public Observable<Void> initialize(List<KarutaQuiz> karutaQuizList) {
        this.karutaQuizCollection = new HashMap<>();
        for (KarutaQuiz karutaQuiz : karutaQuizList) {
            this.karutaQuizCollection.put(karutaQuiz.getIdentifier(), karutaQuiz);
        }
        return Observable.just(null);
    }

    @Override
    public Observable<KarutaQuiz> pop() {
        return Observable.from(karutaQuizCollection.values())
                .filter(karutaQuiz -> karutaQuiz.getResult() == null)
                .firstOrDefault(null);
    }

    @Override
    public Observable<KarutaQuiz> resolve(KarutaQuizIdentifier identifier) {
        return Observable.just(karutaQuizCollection.get(identifier));
    }

    @Override
    public Observable<Void> store(KarutaQuiz karutaQuiz) {
        karutaQuizCollection.remove(karutaQuiz.getIdentifier());
        karutaQuizCollection.put(karutaQuiz.getIdentifier(), karutaQuiz);
        return Observable.just(null);
    }

    @Override
    public Observable<Boolean> existNextQuiz() {
        return Observable.from(karutaQuizCollection.values())
                .filter(karutaQuiz -> karutaQuiz.getResult() == null)
                .count().map(count -> 0 < count);
    }

    @Override
    public Observable<List<KarutaQuiz>> asEntityList() {
        return Observable.just(new ArrayList<>(karutaQuizCollection.values()));
    }
}
