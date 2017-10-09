package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.ExamFragmentViewModel;

@Module
public class ExamFragmentViewModelModule {
    @Provides
    @ForFragment
    ExamFragmentViewModel provideExamFragmentViewModel(KarutaExamModel karutaExamModel,
                                                       Navigator navigator,
                                                       AnalyticsManager analyticsManager) {
        return new ExamFragmentViewModel(karutaExamModel, navigator, analyticsManager);
    }
}
