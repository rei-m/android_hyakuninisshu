package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.di.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizResultFragmentViewModel;

@Module
public class QuizResultFragmentViewModelModule {
    @Provides
    @ForFragment
    QuizResultFragmentViewModel provideQuizResultFragmentViewModel(KarutaTrainingModel karutaTrainingModel,
                                                                   AnalyticsManager analyticsManager) {
        return new QuizResultFragmentViewModel(karutaTrainingModel, analyticsManager);
    }
}
