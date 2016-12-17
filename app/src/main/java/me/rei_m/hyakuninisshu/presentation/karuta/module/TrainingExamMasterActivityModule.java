package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingExamMasterContact;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingExamMasterPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.usecase.karuta.StartTrainingForKarutaExamUsecase;

@Module
public class TrainingExamMasterActivityModule {

    private final Context context;

    public TrainingExamMasterActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForActivity
    Context provideContext() {
        return context;
    }

    @Provides
    TrainingExamMasterContact.Actions provideTrainingExamMasterPresenter(StartTrainingForKarutaExamUsecase startTrainingForKarutaExamUsecase) {
        return new TrainingExamMasterPresenter(startTrainingForKarutaExamUsecase);
    }
}
