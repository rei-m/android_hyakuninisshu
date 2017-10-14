package me.rei_m.hyakuninisshu.viewmodel.karuta.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.di.ForActivity;
import me.rei_m.hyakuninisshu.viewmodel.karuta.ExamMasterActivityViewModel;

@Module
public class ExamMasterActivityViewModelModule {
    @Provides
    @ForActivity
    ExamMasterActivityViewModel provideExamMasterActivityViewModel(KarutaExamModel karutaExamModel) {
        return new ExamMasterActivityViewModel(karutaExamModel);
    }
}
