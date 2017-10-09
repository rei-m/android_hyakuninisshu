package me.rei_m.hyakuninisshu.presentation.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.ExamFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.TrainingMenuFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.module.SupportFragmentModule;

@ForActivity
@Subcomponent(modules = {
        ActivityModule.class,
        TrainingMenuFragmentModule.class,
        ExamFragmentModule.class,
        MaterialFragmentModule.class,
        SupportFragmentModule.class
})
public interface EntranceActivitySubcomponent extends AndroidInjector<EntranceActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<EntranceActivity> {

        public abstract Builder activityModule(ActivityModule module);

        @Override
        public void seedInstance(EntranceActivity instance) {
            activityModule(new ActivityModule(instance));
        }
    }
}
