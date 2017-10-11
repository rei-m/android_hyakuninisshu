package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizAnswerFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizResultFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.viewmodel.karuta.module.TrainingMasterActivityViewModelModule;

@ForActivity
@Subcomponent(modules = {
        ActivityModule.class,
        TrainingMasterActivityViewModelModule.class,
        QuizFragmentModule.class,
        QuizAnswerFragmentModule.class,
        QuizResultFragmentModule.class
})
public interface TrainingMasterActivitySubcomponent extends AndroidInjector<TrainingMasterActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TrainingMasterActivity> {
        public abstract Builder activityModule(ActivityModule module);

        @Override
        public void seedInstance(TrainingMasterActivity instance) {
            activityModule(new ActivityModule(instance));
        }
    }
}
