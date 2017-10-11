package me.rei_m.hyakuninisshu.viewmodel.karuta.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.viewmodel.karuta.TrainingExamMasterActivityViewModel;

@Module
public class TrainingExamMasterActivityViewModelModule {
    @Provides
    @ForActivity
    TrainingExamMasterActivityViewModel provideTrainingExamMasterActivityViewModel(KarutaTrainingModel karutaTrainingModel) {
        return new TrainingExamMasterActivityViewModel(karutaTrainingModel);
    }
}
