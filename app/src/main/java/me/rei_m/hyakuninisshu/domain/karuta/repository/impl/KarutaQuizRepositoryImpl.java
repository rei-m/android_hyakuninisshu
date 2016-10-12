package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import rx.Observable;

public class KarutaQuizRepositoryImpl implements KarutaQuizRepository {

    private List<KarutaQuiz> karutaQuizList;

    @Override
    public Observable<Void> initializeEntityList(List<KarutaQuiz> karutaQuizList) {
        this.karutaQuizList = karutaQuizList;
        return Observable.just(null);
    }

    @Override
    public Observable<List<KarutaQuiz>> asEntityList() {
        return Observable.just(karutaQuizList);
    }
}
