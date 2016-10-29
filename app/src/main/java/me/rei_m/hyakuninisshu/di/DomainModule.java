package me.rei_m.hyakuninisshu.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.impl.KarutaQuizRepositoryImpl;
import me.rei_m.hyakuninisshu.domain.karuta.repository.impl.KarutaRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

@Module
public class DomainModule {

    @Singleton
    @Provides
    KarutaRepository provideKarutaRepository(@ForApplication Context context, OrmaDatabase orma) {
        return new KarutaRepositoryImpl(context, orma);
    }

    @Singleton
    @Provides
    KarutaQuizRepository provideKarutaQuizRepository() {
        return new KarutaQuizRepositoryImpl();
    }

    @Singleton
    @Provides
    KarutaQuizListFactory provideKarutaQuizListFactory(KarutaRepository karutaRepository) {
        return new KarutaQuizListFactory(karutaRepository);
    }
}
