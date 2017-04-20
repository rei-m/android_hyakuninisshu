package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamFragmentViewModel;

@Module
public class ExamFragmentModule {
    private final Context context;

    public ExamFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    ExamFragmentViewModel provideExamFragmentViewModel(KarutaExamModel karutaExamModel,
                                                       Navigator navigator) {
        AnalyticsManager analyticsManager = ((App) context.getApplicationContext()).getAnalyticsManager();
        return new ExamFragmentViewModel(karutaExamModel, navigator, analyticsManager);
    }
}
