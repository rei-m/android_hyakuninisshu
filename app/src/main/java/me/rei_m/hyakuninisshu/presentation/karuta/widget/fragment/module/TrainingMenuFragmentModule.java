package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.TrainingMenuFragmentViewModel;

@Module
public class TrainingMenuFragmentModule {

    private final Context context;

    public TrainingMenuFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    TrainingMenuFragmentViewModel provideTrainingMenuFragmentViewModel(Navigator navigator,
                                                                       AnalyticsManager analyticsManager) {
        return new TrainingMenuFragmentViewModel(analyticsManager, navigator);
    }
}
