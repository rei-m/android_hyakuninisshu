package me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.BuildConfig;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.SupportFragmentViewModel;

@Module
public class SupportFragmentViewModelModule {
    @Provides
    SupportFragmentViewModel provideSupportFragmentViewModel(Context context,
                                                             Navigator navigator,
                                                             AnalyticsManager analyticsManager) {
        String version = context.getString(R.string.version, BuildConfig.VERSION_NAME);
        return new SupportFragmentViewModel(version, navigator, analyticsManager);
    }
}
