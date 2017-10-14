package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialFragmentViewModel;

@Module
public class MaterialFragmentViewModelModule {
    @Provides
    @ForFragment
    MaterialFragmentViewModel provideMaterialFragmentViewModel(KarutaModel karutaModel,
                                                               AnalyticsManager analyticsManager) {
        return new MaterialFragmentViewModel(karutaModel, analyticsManager);
    }
}
