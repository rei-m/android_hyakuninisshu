package me.rei_m.hyakuninisshu.domain.karuta.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.impl.KarutaExamRepositoryImpl;
import me.rei_m.hyakuninisshu.domain.karuta.repository.impl.KarutaQuizRepositoryImpl;
import me.rei_m.hyakuninisshu.domain.karuta.repository.impl.KarutaRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

@Module
public class KarutaDomainModule {

    @Provides
    @Singleton
    KarutaRepository provideKarutaRepository(Context context,
                                             SharedPreferences preferences,
                                             OrmaDatabase orma) {
        return new KarutaRepositoryImpl(context, preferences, orma);
    }

    @Provides
    @Singleton
    KarutaQuizRepository provideKarutaQuizRepository(OrmaDatabase orma) {
        return new KarutaQuizRepositoryImpl(orma);
    }

    @Provides
    @Singleton
    KarutaExamRepository provideExamRepository(OrmaDatabase orma) {
        return new KarutaExamRepositoryImpl(orma);
    }
}
