package me.rei_m.hyakuninisshu.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.impl.KarutaRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

@Module
public class DomainModule {

    @Singleton
    @Provides
    KarutaRepository provideKarutaRepository(OrmaDatabase orma) {
        return new KarutaRepositoryImpl(orma);
    }
}
