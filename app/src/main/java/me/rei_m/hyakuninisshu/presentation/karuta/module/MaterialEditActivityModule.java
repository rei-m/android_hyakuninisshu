package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialEditActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.MaterialEditActivitySubcomponent;

@Module(subcomponents = MaterialEditActivitySubcomponent.class)
public abstract class MaterialEditActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MaterialEditActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMaterialEditActivityInjectorFactory(MaterialEditActivitySubcomponent.Builder builder);
}
