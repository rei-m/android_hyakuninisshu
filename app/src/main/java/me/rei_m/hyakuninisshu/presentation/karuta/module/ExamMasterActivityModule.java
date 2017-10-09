package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.component.ExamMasterActivitySubcomponent;

@Module(subcomponents = ExamMasterActivitySubcomponent.class)
public abstract class ExamMasterActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(ExamMasterActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindExamMasterActivityInjectorFactory(ExamMasterActivitySubcomponent.Builder builder);
}
