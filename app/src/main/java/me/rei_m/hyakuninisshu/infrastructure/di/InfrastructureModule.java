package me.rei_m.hyakuninisshu.infrastructure.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaExamRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaRepositoryImpl;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

@Module
public class InfrastructureModule {

    private static final String KEY_PREFERENCES = "B84UqpLA7W";

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    OrmaDatabase provideOrmaDatabase(@NonNull Context context) {
        return OrmaDatabase.builder(context).build();
    }

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
