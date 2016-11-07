package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.EntranceActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;

@Subcomponent(modules = {ActivityModule.class, EntranceActivityModule.class})
public interface EntranceActivityComponent extends TrainingMenuFragment.Injector {
    void inject(EntranceActivity activity);
}
