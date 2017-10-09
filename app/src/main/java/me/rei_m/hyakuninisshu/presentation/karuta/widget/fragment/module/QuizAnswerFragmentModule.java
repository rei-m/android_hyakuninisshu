package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.QuizAnswerFragmentViewModelModule;

@Module
public abstract class QuizAnswerFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = QuizAnswerFragmentViewModelModule.class)
    abstract QuizAnswerFragment contributeQuizAnswerFragmentInjector();
}
