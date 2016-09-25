package me.rei_m.hyakuninisshu.di;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.SplashActivity;

@Subcomponent(modules = {PresentationModule.class})
public interface ActivityComponent {

    void inject(SplashActivity activity);

    FragmentComponent fragmentComponent();
}
