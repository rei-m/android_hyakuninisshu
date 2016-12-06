package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterContact;
import me.rei_m.hyakuninisshu.presentation.karuta.ExamMasterPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.usecase.karuta.FinishKarutaExamUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaExamUsecase;

@Module
public class ExamMasterActivityModule {

    private final Context context;

    public ExamMasterActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForActivity
    Context provideContext() {
        return context;
    }

    @Provides
    ExamMasterContact.Actions provideQuizMasterPresenter(StartKarutaExamUsecase startKarutaExamUsecase,
                                                         FinishKarutaExamUsecase finishKarutaExamUsecase) {
        return new ExamMasterPresenter(startKarutaExamUsecase, finishKarutaExamUsecase);
    }
}
