package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.TrainingMenuFragmentViewModel;

@Module
public class TrainingMenuFragmentViewModelModule {
    @Provides
    @ForFragment
    TrainingMenuFragmentViewModel provideTrainingMenuFragmentViewModel(Navigator navigator,
                                                                       AnalyticsManager analyticsManager) {
        return new TrainingMenuFragmentViewModel(analyticsManager, navigator);
    }
}
