package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizAnswerFragmentViewModel;

@Module
public class QuizAnswerFragmentModule {

    private final Context context;

    public QuizAnswerFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    QuizAnswerFragmentViewModel provideQuizAnswerFragmentViewModel(KarutaModel karutaModel,
                                                                   Navigator navigator) {
        return new QuizAnswerFragmentViewModel(karutaModel, navigator);
    }
}
