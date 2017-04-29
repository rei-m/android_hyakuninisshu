package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizResultFragmentViewModel;

@Module
public class QuizResultFragmentModule {

    private final Context context;

    public QuizResultFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    QuizResultFragmentViewModel provideQuizResultFragmentViewModel(KarutaTrainingModel karutaTrainingModel,
                                                                   AnalyticsManager analyticsManager) {
        return new QuizResultFragmentViewModel(karutaTrainingModel, analyticsManager);
    }
}
