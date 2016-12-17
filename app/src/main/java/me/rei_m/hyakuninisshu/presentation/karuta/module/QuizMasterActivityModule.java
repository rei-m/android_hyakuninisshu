package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMasterContact;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMasterPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

@Module
public class QuizMasterActivityModule {

    private final Context context;

    public QuizMasterActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForActivity
    Context provideContext() {
        return context;
    }
    
    @Provides
    TrainingMasterContact.Actions provideQuizMasterPresenter(StartKarutaQuizUsecase startKarutaQuizUsecase) {
        return new TrainingMasterPresenter(startKarutaQuizUsecase);
    }
}
