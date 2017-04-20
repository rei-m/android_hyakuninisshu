package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamResultFragmentViewModel;

@Module
public class ExamResultFragmentModule {

    private final Context context;

    public ExamResultFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    ExamResultFragmentViewModel provideExamResultFragmentViewModel(KarutaExamModel karutaExamModel) {
        AnalyticsManager analyticsManager = ((App) context.getApplicationContext()).getAnalyticsManager();
        return new ExamResultFragmentViewModel(karutaExamModel, analyticsManager);
    }
}
