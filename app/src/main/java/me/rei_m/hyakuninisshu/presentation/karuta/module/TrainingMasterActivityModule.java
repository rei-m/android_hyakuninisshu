package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.TrainingMasterActivitySubcomponent;

@Module(subcomponents = TrainingMasterActivitySubcomponent.class)
public abstract class TrainingMasterActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(TrainingMasterActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindTrainingMasterActivityInjectorFactory(TrainingMasterActivitySubcomponent.Builder builder);
}
