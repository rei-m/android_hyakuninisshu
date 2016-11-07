package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.content.Context;

import dagger.Module;

@Module
public class SplashActivityModule {

    private final Context context;

    public SplashActivityModule(Context context) {
        this.context = context;
    }
}
