package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;

@Subcomponent(modules = {FragmentModule.class, MaterialFragmentModule.class})
public interface MaterialFragmentComponent {
    void inject(MaterialFragment fragment);
}
