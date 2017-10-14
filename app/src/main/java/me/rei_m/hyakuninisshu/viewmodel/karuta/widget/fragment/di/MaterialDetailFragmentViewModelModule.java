package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialDetailFragmentViewModel;

@Module
public class MaterialDetailFragmentViewModelModule {
    @Provides
    @ForFragment
    MaterialDetailFragmentViewModel provideMaterialDetailFragmentViewModel(KarutaModel karutaModel) {
        return new MaterialDetailFragmentViewModel(karutaModel);
    }
}
