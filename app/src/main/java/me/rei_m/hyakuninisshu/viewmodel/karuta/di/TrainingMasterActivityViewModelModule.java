package me.rei_m.hyakuninisshu.viewmodel.karuta.di;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.viewmodel.karuta.TrainingMasterActivityViewModel;

@Module
public class TrainingMasterActivityViewModelModule {
    @Provides
    @ForActivity
    TrainingMasterActivityViewModel provideTrainingMasterActivityViewModel(@NonNull KarutaTrainingModel karutaTrainingModel) {
        return new TrainingMasterActivityViewModel(karutaTrainingModel);
    }
}
