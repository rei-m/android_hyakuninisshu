package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialSingleActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.MaterialSingleActivitySubcomponent;

@Module(subcomponents = MaterialSingleActivitySubcomponent.class)
public abstract class MaterialSingleActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MaterialSingleActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMaterialSingleActivityInjectorFactory(MaterialSingleActivitySubcomponent.Builder builder);
}
