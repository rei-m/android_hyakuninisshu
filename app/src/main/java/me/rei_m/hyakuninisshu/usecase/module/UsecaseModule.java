package me.rei_m.hyakuninisshu.usecase.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.usecase.StartApplicationUsecase;
import me.rei_m.hyakuninisshu.usecase.impl.StartApplicationUsecaseImpl;

@Module
public class UsecaseModule {
    @Provides
    StartApplicationUsecase provideStartApplicationUsecase(KarutaRepository karutaRepository) {
        return new StartApplicationUsecaseImpl(karutaRepository);
    }
}
