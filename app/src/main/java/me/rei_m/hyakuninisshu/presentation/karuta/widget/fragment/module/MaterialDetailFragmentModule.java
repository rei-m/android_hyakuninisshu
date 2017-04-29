package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialDetailFragmentViewModel;

@Module
public class MaterialDetailFragmentModule {

    private final Context context;

    public MaterialDetailFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    MaterialDetailFragmentViewModel provideMaterialDetailFragmentViewModel(KarutaModel karutaModel) {
        return new MaterialDetailFragmentViewModel(karutaModel);
    }
}
