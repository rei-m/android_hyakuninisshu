package me.rei_m.hyakuninisshu.presentation.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.presentation.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.component.EntranceActivitySubcomponent;

@Module(subcomponents = EntranceActivitySubcomponent.class)
public abstract class EntranceActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(EntranceActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindEntranceActivityInjectorFactory(EntranceActivitySubcomponent.Builder builder);
}
