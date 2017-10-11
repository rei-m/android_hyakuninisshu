package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamResultFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.ExamResultFragmentViewModelModule;

@Module
public abstract class ExamResultFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = ExamResultFragmentViewModelModule.class)
    abstract ExamResultFragment contributeExamResultFragmentInjector();
}
