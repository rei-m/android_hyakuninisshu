package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.RestartWrongKarutaQuizUsecase;

public class RestartWrongKarutaQuizUsecaseImpl implements RestartWrongKarutaQuizUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaQuizListFactory karutaQuizListFactory;

    public RestartWrongKarutaQuizUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository,
                                             @NonNull KarutaQuizListFactory karutaQuizListFactory) {
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
    }

    @Override
    public Completable execute() {
        return karutaQuizRepository.asEntityList()
                .flatMap(karutaQuizList -> Observable.fromIterable(karutaQuizList)
                        .filter(karutaQuiz -> karutaQuiz.getResult() != null && !karutaQuiz.getResult().isCollect)
                        .map(karutaQuiz -> karutaQuiz.getResult().collectKarutaId)
                        .toList())
                .flatMap(karutaQuizListFactory::create)
                .flatMapCompletable(karutaQuizList -> {
                    if (karutaQuizList.isEmpty()) {
                        return Completable.error(new IllegalStateException("Wrong quiz is not exist."));
                    } else {
                        return karutaQuizRepository.initialize(karutaQuizList);
                    }
                });
    }
}
