package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.ExamFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizAnswerFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizFragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizResultFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.viewmodel.karuta.module.TrainingExamMasterActivityViewModelModule;

@ForActivity
@Subcomponent(modules = {
        ActivityModule.class,
        TrainingExamMasterActivityViewModelModule.class,
        ExamFragmentModule.class,
        QuizAnswerFragmentModule.class,
        QuizFragmentModule.class,
        QuizResultFragmentModule.class
})
public interface TrainingExamMasterActivitySubcomponent extends AndroidInjector<TrainingExamMasterActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TrainingExamMasterActivity> {
        public abstract Builder activityModule(ActivityModule module);

        @Override
        public void seedInstance(TrainingExamMasterActivity instance) {
            activityModule(new ActivityModule(instance));
        }
    }
}
