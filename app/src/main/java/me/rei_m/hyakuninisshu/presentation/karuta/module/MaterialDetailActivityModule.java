package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialDetailActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.MaterialDetailActivitySubcomponent;

@Module(subcomponents = MaterialDetailActivitySubcomponent.class)
public abstract class MaterialDetailActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MaterialDetailActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMaterialDetailActivityInjectorFactory(MaterialDetailActivitySubcomponent.Builder builder);
}
