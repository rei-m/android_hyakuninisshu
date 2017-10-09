package me.rei_m.hyakuninisshu.infrastructure.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
}
