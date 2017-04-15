package me.rei_m.hyakuninisshu.presentation.karuta.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.TrainingExamMasterActivityViewModel;

@Module
public class TrainingExamMasterActivityModule {
    @Provides
    TrainingExamMasterActivityViewModel provideTrainingExamMasterActivityViewModel(KarutaTrainingModel karutaTrainingModel) {
        return new TrainingExamMasterActivityViewModel(karutaTrainingModel);
    }
}
