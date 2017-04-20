package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.model.KarutaQuizModel;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment.QuizFragmentViewModel;

@Module
public class QuizFragmentModule {

    private final Context context;

    public QuizFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    QuizFragmentViewModel provideQuizFragmentViewModel(KarutaQuizModel karutaQuizModel) {
        return new QuizFragmentViewModel(karutaQuizModel);
    }
}
