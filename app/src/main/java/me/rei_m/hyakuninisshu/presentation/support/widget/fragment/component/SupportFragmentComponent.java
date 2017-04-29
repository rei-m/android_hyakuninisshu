package me.rei_m.hyakuninisshu.presentation.support.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.SupportFragment;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.module.SupportFragmentModule;

@Subcomponent(modules = {FragmentModule.class, SupportFragmentModule.class})
public interface SupportFragmentComponent {
    void inject(SupportFragment fragment);
}
