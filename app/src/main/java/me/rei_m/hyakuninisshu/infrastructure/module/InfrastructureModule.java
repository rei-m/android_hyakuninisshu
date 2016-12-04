package me.rei_m.hyakuninisshu.infrastructure.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;
import me.rei_m.hyakuninisshu.module.ForApplication;

@Module
public class InfrastructureModule {

    private static final String KEY_PREFERENCES = "B84UqpLA7W";

    @Provides
    @ForApplication
    SharedPreferences provideSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @ForApplication
    OrmaDatabase provideOrmaDatabase(@NonNull Context context) {
        return OrmaDatabase.builder(context).build();
    }
}
