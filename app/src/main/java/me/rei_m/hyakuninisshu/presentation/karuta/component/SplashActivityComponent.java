package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.SplashActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.SplashActivityModule;

@Subcomponent(modules = {ActivityModule.class, SplashActivityModule.class})
public interface SplashActivityComponent {
    void inject(SplashActivity activity);
}
