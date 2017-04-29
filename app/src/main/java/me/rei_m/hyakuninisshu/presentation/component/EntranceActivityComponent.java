package me.rei_m.hyakuninisshu.presentation.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.EntranceActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuFragment;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.EntranceActivityModule;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.SupportFragment;

@Subcomponent(modules = {ActivityModule.class, EntranceActivityModule.class})
public interface EntranceActivityComponent extends TrainingMenuFragment.Injector,
        ExamFragment.Injector,
        MaterialFragment.Injector,
        SupportFragment.Injector {
    void inject(EntranceActivity activity);
}
