package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamPresenter;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayExamUsecase;

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
    ExamContact.Actions provideQuizAnswerPresenter(DisplayExamUsecase displayExamUsecase) {
        AnalyticsManager analyticsManager = ((App) context.getApplicationContext()).getAnalyticsManager();
        return new ExamPresenter(displayExamUsecase, analyticsManager);
    }
}
