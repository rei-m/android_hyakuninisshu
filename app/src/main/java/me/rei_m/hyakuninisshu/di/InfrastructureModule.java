package me.rei_m.hyakuninisshu.di;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class InfrastructureModule {

    private static final String KEY_PREFERENCES = "B84UqpLA7W";

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(@ForApplication Context context) {
        return context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {

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
