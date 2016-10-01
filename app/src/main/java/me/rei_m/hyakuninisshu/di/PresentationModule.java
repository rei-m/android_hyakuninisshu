package me.rei_m.hyakuninisshu.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMenuContact;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMenuPresenter;

@Module
class PresentationModule {

    @Provides
    ActivityNavigator provideActivityNavigator() {
        return new ActivityNavigator();
    }

    @Provides
    TrainingMenuContact.Actions provideTrainingMenuPresenter() {
        return new TrainingMenuPresenter();
    }
}
