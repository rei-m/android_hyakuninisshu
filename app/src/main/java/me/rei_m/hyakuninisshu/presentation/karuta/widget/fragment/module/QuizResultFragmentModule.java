package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultPresenter;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizResultUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.RestartWrongKarutaQuizUsecase;

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
    QuizResultContact.Actions provideQuizResultPresenter(DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase,
                                                         RestartWrongKarutaQuizUsecase restartWrongKarutaQuizUsecase) {
        AnalyticsManager analyticsManager = ((App) context.getApplicationContext()).getAnalyticsManager();
        return new QuizResultPresenter(displayKarutaQuizResultUsecase, restartWrongKarutaQuizUsecase, analyticsManager);
    }
}
