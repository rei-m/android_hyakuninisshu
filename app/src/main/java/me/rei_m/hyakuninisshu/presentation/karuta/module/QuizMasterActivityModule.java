package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterContact;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterPresenter;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

@Module
public class QuizMasterActivityModule {

    private final Context context;

    public QuizMasterActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    QuizMasterContact.Actions provideQuizMasterPresenter(StartKarutaQuizUsecase startKarutaQuizUsecase) {
        return new QuizMasterPresenter(startKarutaQuizUsecase);
    }
}
