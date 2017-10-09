package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.QuizFragmentViewModelModule;

@Module
public abstract class QuizFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = QuizFragmentViewModelModule.class)
    abstract QuizFragment contributeQuizFragmentInjector();
}
