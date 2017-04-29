package me.rei_m.hyakuninisshu.presentation.support.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.BuildConfig;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.SupportFragmentViewModel;

@Module
public class SupportFragmentModule {

    private final Context context;

    public SupportFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    SupportFragmentViewModel provideSupportFragmentViewModel(Navigator navigator,
                                                             AnalyticsManager analyticsManager) {
        String version = context.getString(R.string.version, BuildConfig.VERSION_NAME);
        return new SupportFragmentViewModel(version, navigator, analyticsManager);
    }
}
