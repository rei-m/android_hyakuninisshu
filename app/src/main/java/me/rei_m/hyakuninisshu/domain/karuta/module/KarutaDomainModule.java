package me.rei_m.hyakuninisshu.domain.karuta.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.impl.KarutaQuizRepositoryImpl;
import me.rei_m.hyakuninisshu.domain.karuta.repository.impl.KarutaRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;
import me.rei_m.hyakuninisshu.module.ForApplication;

@Module
public class KarutaDomainModule {

    @Provides
    @ForApplication
    KarutaRepository provideKarutaRepository(Context context, OrmaDatabase orma) {
        return new KarutaRepositoryImpl(context, orma);
    }

    @Provides
    @ForApplication
    KarutaQuizRepository provideKarutaQuizRepository() {
        return new KarutaQuizRepositoryImpl();
    }

    @Provides
    @ForApplication
    KarutaQuizListFactory provideKarutaQuizListFactory(KarutaRepository karutaRepository) {
        return new KarutaQuizListFactory(karutaRepository);
    }
}