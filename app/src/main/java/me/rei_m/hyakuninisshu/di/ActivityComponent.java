package me.rei_m.hyakuninisshu.di;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class})
public interface ActivityComponent {

    FragmentComponent fragmentComponent();
}
