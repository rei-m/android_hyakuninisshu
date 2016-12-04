package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.ExamMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;

@Subcomponent(modules = {ActivityModule.class, ExamMasterActivityModule.class})
public interface ExamMasterActivityComponent extends QuizAnswerFragment.Injector,
        QuizFragment.Injector {
    void inject(ExamMasterActivity activity);
}
