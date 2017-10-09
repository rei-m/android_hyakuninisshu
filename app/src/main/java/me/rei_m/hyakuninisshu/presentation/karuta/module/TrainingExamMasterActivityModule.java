package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.TrainingExamMasterActivitySubcomponent;

@Module(subcomponents = TrainingExamMasterActivitySubcomponent.class)
public abstract class TrainingExamMasterActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(TrainingExamMasterActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindTrainingExamMasterActivityInjectorFactory(TrainingExamMasterActivitySubcomponent.Builder builder);
}
