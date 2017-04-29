package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialEditFragmentViewModel;

@Module
public class MaterialEditFragmentModule {

    private final Context context;

    public MaterialEditFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    MaterialEditFragmentViewModel provideMaterialEditFragmentViewModel(KarutaModel karutaModel) {
        return new MaterialEditFragmentViewModel(karutaModel);
    }
}
