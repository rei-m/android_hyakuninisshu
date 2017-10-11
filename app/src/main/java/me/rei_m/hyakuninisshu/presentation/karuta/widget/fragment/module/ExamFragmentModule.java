package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.ExamFragmentViewModelModule;

@Module
public abstract class ExamFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = ExamFragmentViewModelModule.class)
    abstract ExamFragment contributeExamFragmentInjector();
}
