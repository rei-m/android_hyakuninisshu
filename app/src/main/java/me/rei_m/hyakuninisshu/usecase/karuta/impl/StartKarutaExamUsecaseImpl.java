package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaExamUsecase;

public class StartKarutaExamUsecaseImpl implements StartKarutaExamUsecase {

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaQuizListFactory karutaQuizListFactory;

    public StartKarutaExamUsecaseImpl(@NonNull KarutaRepository karutaRepository,
                                      @NonNull KarutaQuizRepository karutaQuizRepository,
                                      @NonNull KarutaQuizListFactory karutaQuizListFactory) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
    }

    @Override
    public Completable execute() {
        return karutaRepository.asEntityList()
                .flatMap(karutaList -> Observable.fromIterable(karutaList).map(AbstractEntity::getIdentifier).toList())
                .flatMap(karutaQuizListFactory::create)
                .flatMapCompletable(karutaQuizRepository::initialize);
    }
}
