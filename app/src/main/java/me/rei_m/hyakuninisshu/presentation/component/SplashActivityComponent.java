package me.rei_m.hyakuninisshu.presentation.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.SplashActivity;
import me.rei_m.hyakuninisshu.presentation.module.SplashActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;

@Subcomponent(modules = {ActivityModule.class, SplashActivityModule.class})
public interface SplashActivityComponent {
    void inject(SplashActivity activity);
}
