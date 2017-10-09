package me.rei_m.hyakuninisshu.presentation.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.presentation.SplashActivity;
import me.rei_m.hyakuninisshu.presentation.component.SplashActivitySubcomponent;

@Module(subcomponents = SplashActivitySubcomponent.class)
public abstract class SplashActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(SplashActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSplashActivityInjectorFactory(SplashActivitySubcomponent.Builder builder);
}
