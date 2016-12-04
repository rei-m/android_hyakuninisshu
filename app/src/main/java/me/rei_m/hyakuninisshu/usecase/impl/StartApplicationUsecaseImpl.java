package me.rei_m.hyakuninisshu.usecase.impl;

import io.reactivex.Completable;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.usecase.StartApplicationUsecase;

public class StartApplicationUsecaseImpl implements StartApplicationUsecase {

    private final KarutaRepository karutaRepository;

    public StartApplicationUsecaseImpl(KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    @Override
    public Completable execute() {
        return karutaRepository.initializeEntityList();
    }
}
