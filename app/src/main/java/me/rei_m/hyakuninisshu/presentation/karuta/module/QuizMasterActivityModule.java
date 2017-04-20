package me.rei_m.hyakuninisshu.presentation.karuta.module;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.TrainingMasterActivityViewModel;

@Module
public class QuizMasterActivityModule {
    @Provides
    TrainingMasterActivityViewModel provideTrainingMasterActivityViewModel(@NonNull KarutaTrainingModel karutaTrainingModel) {
        return new TrainingMasterActivityViewModel(karutaTrainingModel);
    }
}
