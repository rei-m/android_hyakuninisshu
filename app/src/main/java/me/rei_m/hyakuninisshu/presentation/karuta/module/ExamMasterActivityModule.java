package me.rei_m.hyakuninisshu.presentation.karuta.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.viewmodel.karuta.ExamMasterActivityViewModel;

@Module
public class ExamMasterActivityModule {

    @Provides
    ExamMasterActivityViewModel provideExamMasterActivityViewModel(KarutaExamModel karutaExamModel) {
        return new ExamMasterActivityViewModel(karutaExamModel);
    }
}
