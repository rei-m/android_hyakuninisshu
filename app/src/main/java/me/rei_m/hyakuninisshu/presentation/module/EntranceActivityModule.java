package me.rei_m.hyakuninisshu.presentation.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;

@Module
public class EntranceActivityModule {

    private final Context context;

    public EntranceActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForActivity
    Context provideContext() {
        return context;
    }
}
