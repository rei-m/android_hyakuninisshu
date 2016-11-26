package me.rei_m.hyakuninisshu.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(@NonNull Application application) {
        this.context = application.getApplicationContext();
    }

    @Provides
    @ForApplication
    Context provideContext() {
        return context;
    }
}
