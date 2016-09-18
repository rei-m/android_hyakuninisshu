package me.rei_m.hyakuninisshu.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;

@Module
public class PresentationModule {

    @Provides
    @Singleton
    public ActivityNavigator provideActivityNavigator() {
        return new ActivityNavigator();
    }
}
