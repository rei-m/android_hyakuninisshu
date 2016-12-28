package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialPresenter;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialUsecase;

@Module
public class MaterialFragmentModule {

    private final Context context;

    public MaterialFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    MaterialContact.Actions provideTrainingMenuPresenter(DisplayMaterialUsecase displayMaterialUsecase) {
        AnalyticsManager analyticsManager = ((App) context.getApplicationContext()).getAnalyticsManager();
        return new MaterialPresenter(displayMaterialUsecase, analyticsManager);
    }
}
