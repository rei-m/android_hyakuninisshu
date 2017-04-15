package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.MaterialFragmentViewModel;

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

//    @Provides
//    MaterialContact.Actions provideTrainingMenuPresenter(DisplayMaterialUsecase displayMaterialUsecase) {
//        AnalyticsManager analyticsManager = ((App) context.getApplicationContext()).getAnalyticsManager();
//        return new MaterialPresenter(displayMaterialUsecase, analyticsManager);
//    }

    @Provides
    MaterialFragmentViewModel provideMaterialFragmentViewModel(KarutaModel karutaModel) {
        AnalyticsManager analyticsManager = ((App) context.getApplicationContext()).getAnalyticsManager();
        return new MaterialFragmentViewModel(karutaModel, analyticsManager);
    }
}
