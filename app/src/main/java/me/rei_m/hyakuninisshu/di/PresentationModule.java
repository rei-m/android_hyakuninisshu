package me.rei_m.hyakuninisshu.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;

@Module
class PresentationModule {

    @Provides
    ActivityNavigator provideActivityNavigator() {
        return new ActivityNavigator();
    }
}
