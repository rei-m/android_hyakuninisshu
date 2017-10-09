package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.ExamResultFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizAnswerFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.viewmodel.karuta.module.ExamMasterActivityViewModelModule;

@ForActivity
@Subcomponent(modules = {
        ActivityModule.class,
        ExamMasterActivityViewModelModule.class,
        QuizAnswerFragmentModule.class,
        QuizFragmentModule.class,
        ExamResultFragmentModule.class
})
public interface ExamMasterActivitySubcomponent extends AndroidInjector<ExamMasterActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ExamMasterActivity> {
        public abstract Builder activityModule(ActivityModule module);

        @Override
        public void seedInstance(ExamMasterActivity instance) {
            activityModule(new ActivityModule(instance));
        }
    }
}
