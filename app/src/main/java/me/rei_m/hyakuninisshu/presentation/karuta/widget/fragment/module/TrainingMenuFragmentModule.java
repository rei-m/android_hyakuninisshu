package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuPresenter;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;

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
    TrainingMenuContact.Actions provideTrainingMenuPresenter() {
        AnalyticsManager analyticsManager = ((App) context.getApplicationContext()).getAnalyticsManager();
        return new TrainingMenuPresenter(analyticsManager);
    }
}
