package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.content.Context;

import dagger.Module;

@Module
public class EntranceActivityModule {

    private final Context context;

    public EntranceActivityModule(Context context) {
        this.context = context;
    }
}
