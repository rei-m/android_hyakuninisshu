package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;

@Module
public class ActivityModule {

    private final Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    ActivityNavigator provideActivityNavigator() {
        return new ActivityNavigator();
    }
}
