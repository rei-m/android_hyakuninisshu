package me.rei_m.hyakuninisshu.presentation.support.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.SupportFragment;
import me.rei_m.hyakuninisshu.viewmodel.support.widget.fragment.module.SupportFragmentViewModelModule;

@Module
public abstract class SupportFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = SupportFragmentViewModelModule.class)
    abstract SupportFragment contributeMaterialFragmentInjector();
}
