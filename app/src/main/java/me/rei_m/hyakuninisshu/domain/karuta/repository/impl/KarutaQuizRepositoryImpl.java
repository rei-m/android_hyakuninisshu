package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizResult;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import rx.Observable;

public class KarutaQuizRepositoryImpl implements KarutaQuizRepository {

    private List<KarutaQuiz> karutaQuizList;

    private List<KarutaQuizResult> resultList = new ArrayList<>();

    @Override
    public Observable<Void> initialize(List<KarutaQuiz> karutaQuizList) {
        this.karutaQuizList = karutaQuizList;
        this.resultList = new ArrayList<>();
        return Observable.just(null);
    }

    @Override
    public Observable<KarutaQuiz> pop() {
        return Observable.from(karutaQuizList).firstOrDefault(null);
    }

    @Override
    public Observable<Void> storeResult(KarutaQuiz karutaQuiz, KarutaQuizResult karutaQuizResult) {
        Iterator<KarutaQuiz> iterator = karutaQuizList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getIdentifier().equals(karutaQuiz.getIdentifier())) {
                iterator.remove();
                resultList.add(karutaQuizResult);
                break;
            }
        }
        return Observable.just(null);
    }
}
