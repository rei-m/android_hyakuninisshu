package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialDetailFragmentViewModel;

@Module
public class MaterialDetailFragmentViewModelModule {
    @Provides
    @ForFragment
    MaterialDetailFragmentViewModel provideMaterialDetailFragmentViewModel(KarutaModel karutaModel) {
        return new MaterialDetailFragmentViewModel(karutaModel);
    }
}
