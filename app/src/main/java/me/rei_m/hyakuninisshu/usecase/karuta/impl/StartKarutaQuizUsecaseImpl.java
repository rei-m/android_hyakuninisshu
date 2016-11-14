package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

public class StartKarutaQuizUsecaseImpl implements StartKarutaQuizUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaQuizListFactory karutaQuizListFactory;

    public StartKarutaQuizUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository,
                                      @NonNull KarutaQuizListFactory karutaQuizListFactory) {
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
    }

    @Override
    public Completable execute(int fromKarutaId, int toKarutaId, int kimarijiPosition) {
        return karutaQuizListFactory.create(fromKarutaId, toKarutaId, kimarijiPosition).flatMapCompletable(karutaQuizList -> {
            if (karutaQuizList.isEmpty()) {
                return Completable.error(new IllegalArgumentException("This condition is not valid."));
            } else {
                return karutaQuizRepository.initialize(karutaQuizList);
            }
        });
    }
}
