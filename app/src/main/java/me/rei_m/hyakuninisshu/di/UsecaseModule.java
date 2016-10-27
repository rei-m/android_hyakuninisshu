package me.rei_m.hyakuninisshu.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.AnswerKarutaQuizUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.DisplayKarutaQuizUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.StartKarutaQuizUsecaseImpl;

@Module
public class UsecaseModule {

    @Provides
    StartKarutaQuizUsecase provideStartKarutaQuizUsecase(KarutaQuizRepository karutaQuizRepository,
                                                         KarutaQuizListFactory karutaQuizListFactory) {
        return new StartKarutaQuizUsecaseImpl(karutaQuizRepository, karutaQuizListFactory);
    }

    @Provides
    DisplayKarutaQuizUsecase provideDisplayKarutaQuizUsecase(KarutaRepository karutaRepository,
                                                             KarutaQuizRepository karutaQuizRepository) {
        return new DisplayKarutaQuizUsecaseImpl(karutaRepository, karutaQuizRepository);
    }

    @Provides
    AnswerKarutaQuizUsecase provideAnswerKarutaQuizUsecase(KarutaQuizRepository karutaQuizRepository) {
        return new AnswerKarutaQuizUsecaseImpl(karutaQuizRepository);
    }
}
