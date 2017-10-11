package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module.TrainingMenuFragmentViewModelModule;

@Module
public abstract class TrainingMenuFragmentModule {
    @ForFragment
    @ContributesAndroidInjector(modules = TrainingMenuFragmentViewModelModule.class)
    abstract TrainingMenuFragment contributeTrainingMenuFragmentInjector();
}
