package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.QuizMasterActivityModule;

@Subcomponent(modules = {ActivityModule.class, QuizMasterActivityModule.class})
public interface QuizMasterActivityComponent {
    void inject(QuizMasterActivity activity);
}
