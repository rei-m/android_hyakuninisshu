package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.QuizResultFragmentViewModelModule;

@Module
public abstract class QuizResultFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = QuizResultFragmentViewModelModule.class)
    abstract QuizResultFragment contributeQuizResultFragmentInjector();
}
