package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.TrainingMenuFragmentModule;

@Subcomponent(modules = {FragmentModule.class, TrainingMenuFragmentModule.class})
public interface TrainingMenuFragmentComponent {
    void inject(TrainingMenuFragment fragment);
}
