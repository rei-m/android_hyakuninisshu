package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialEditFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.MaterialEditFragmentViewModelModule;

@Module
public abstract class MaterialEditFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = MaterialEditFragmentViewModelModule.class)
    abstract MaterialEditFragment contributeMaterialEditFragmentInjector();
}
