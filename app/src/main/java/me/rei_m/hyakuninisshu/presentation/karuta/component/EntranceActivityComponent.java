package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.EntranceActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;

@Subcomponent(modules = {ActivityModule.class, EntranceActivityModule.class})
public interface EntranceActivityComponent extends TrainingMenuFragment.Injector {
    void inject(EntranceActivity activity);
}
