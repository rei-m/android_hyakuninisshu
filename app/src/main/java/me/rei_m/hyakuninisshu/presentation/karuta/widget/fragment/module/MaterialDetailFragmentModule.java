package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialDetailUsecase;

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
    MaterialDetailContact.Actions provideTrainingMenuPresenter(DisplayMaterialDetailUsecase displayMaterialDetailUsecase) {
        return new MaterialDetailPresenter(displayMaterialDetailUsecase);
    }
}
