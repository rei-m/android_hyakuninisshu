package me.rei_m.hyakuninisshu.di;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMenuFragment;

@Subcomponent
public interface FragmentComponent {
    void inject(TrainingMenuFragment fragment);
}
