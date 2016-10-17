package me.rei_m.hyakuninisshu.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.StartKarutaQuizUsecaseImpl;

@Module
public class UsecaseModule {

    @Provides
    StartKarutaQuizUsecase provideStartKarutaQuizUsecase(KarutaQuizRepository karutaQuizRepository,
                                                         KarutaQuizListFactory karutaQuizListFactory) {
        return new StartKarutaQuizUsecaseImpl(karutaQuizRepository, karutaQuizListFactory);
    }

}
