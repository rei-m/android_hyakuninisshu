package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.adapter.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter.MaterialKarutaListAdapter;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.adapter.KarutaListItemViewModel;

@Module
public class KarutaListItemViewModelModule {
    @Provides
    MaterialKarutaListAdapter.Injector provideMaterialKarutaListAdapterInjector(Navigator navigator) {
        return () -> new KarutaListItemViewModel(navigator);
    }
}
