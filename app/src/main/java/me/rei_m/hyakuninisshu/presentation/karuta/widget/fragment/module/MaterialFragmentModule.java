package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.adapter.module.KarutaListItemViewModelModule;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.MaterialFragmentViewModelModule;

@Module
public abstract class MaterialFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = {
            MaterialFragmentViewModelModule.class,
            KarutaListItemViewModelModule.class
    })
    abstract MaterialFragment contributeMaterialFragmentInjector();
}
