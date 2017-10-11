package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialEditFragmentViewModel;

@Module
public class MaterialEditFragmentViewModelModule {
    @Provides
    @ForFragment
    MaterialEditFragmentViewModel provideMaterialEditFragmentViewModel(KarutaModel karutaModel) {
        return new MaterialEditFragmentViewModel(karutaModel);
    }
}
