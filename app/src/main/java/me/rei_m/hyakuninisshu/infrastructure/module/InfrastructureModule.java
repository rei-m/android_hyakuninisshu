package me.rei_m.hyakuninisshu.infrastructure.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.BuildConfig;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;
import me.rei_m.hyakuninisshu.module.ForApplication;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class InfrastructureModule {

    private static final String KEY_PREFERENCES = "B84UqpLA7W";

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@ForApplication Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    OrmaDatabase provideOrmaDatabase(@ForApplication Context context) {
        return OrmaDatabase.builder(context)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return client.newBuilder().addInterceptor(logging).build();
    }
}
