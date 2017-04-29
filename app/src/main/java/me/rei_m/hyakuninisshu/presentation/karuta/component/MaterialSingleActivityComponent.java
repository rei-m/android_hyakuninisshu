package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialSingleActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialSingleActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;

@Subcomponent(modules = {ActivityModule.class, MaterialSingleActivityModule.class})
public interface MaterialSingleActivityComponent extends MaterialDetailFragment.Injector {
    void inject(MaterialSingleActivity activity);
}
