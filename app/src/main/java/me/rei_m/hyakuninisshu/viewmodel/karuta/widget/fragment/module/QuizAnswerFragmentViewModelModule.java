package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.module;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizAnswerFragmentViewModel;

@Module
public class QuizAnswerFragmentViewModelModule {
    @Provides
    @ForFragment
    QuizAnswerFragmentViewModel provideQuizAnswerFragmentViewModel(KarutaModel karutaModel,
                                                                   Navigator navigator) {
        return new QuizAnswerFragmentViewModel(karutaModel, navigator);
    }
}
