package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialEditFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialEditFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;

@Subcomponent(modules = {FragmentModule.class, MaterialEditFragmentModule.class})
public interface MaterialEditFragmentComponent {
    void inject(MaterialEditFragment fragment);
}
