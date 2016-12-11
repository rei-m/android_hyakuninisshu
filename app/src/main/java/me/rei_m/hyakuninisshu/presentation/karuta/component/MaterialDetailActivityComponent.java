package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialDetailActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialDetailActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;

@Subcomponent(modules = {ActivityModule.class, MaterialDetailActivityModule.class})
public interface MaterialDetailActivityComponent {
    void inject(MaterialDetailActivity activity);
}
