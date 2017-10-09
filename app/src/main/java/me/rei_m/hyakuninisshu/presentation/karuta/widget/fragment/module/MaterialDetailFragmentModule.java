package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.MaterialDetailFragmentViewModelModule;

@Module
public abstract class MaterialDetailFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = MaterialDetailFragmentViewModelModule.class)
    abstract MaterialDetailFragment contributeMaterialDetailFragmentInjector();
}
