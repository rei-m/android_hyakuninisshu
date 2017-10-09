package me.rei_m.hyakuninisshu.viewmodel.karuta.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;
import me.rei_m.hyakuninisshu.viewmodel.karuta.ExamMasterActivityViewModel;

@Module
public class ExamMasterActivityViewModelModule {
    @Provides
    @ForActivity
    ExamMasterActivityViewModel provideExamMasterActivityViewModel(KarutaExamModel karutaExamModel) {
        return new ExamMasterActivityViewModel(karutaExamModel);
    }
}
