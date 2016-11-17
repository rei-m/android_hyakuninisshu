package me.rei_m.hyakuninisshu.presentation.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;

@Module
public class ActivityModule {

    @Provides
    ActivityNavigator provideActivityNavigator() {
        return new ActivityNavigator();
    }
}
