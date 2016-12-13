package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialDetailFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;

@Subcomponent(modules = {FragmentModule.class, MaterialDetailFragmentModule.class})
public interface MaterialDetailFragmentComponent {
    void inject(MaterialDetailFragment fragment);
}
