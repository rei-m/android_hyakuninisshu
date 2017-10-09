package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaQuizModel;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizFragmentViewModel;

@Module
public class QuizFragmentViewModelModule {
    @Provides
    @ForFragment
    QuizFragmentViewModel provideQuizFragmentViewModel(KarutaQuizModel karutaQuizModel) {
        return new QuizFragmentViewModel(karutaQuizModel);
    }
}
