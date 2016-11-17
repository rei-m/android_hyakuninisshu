package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizAnswerUsecase;

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
    QuizAnswerContact.Actions provideQuizAnswerPresenter(DisplayKarutaQuizAnswerUsecase displayKarutaQuizAnswerUsecase) {
        return new QuizAnswerPresenter(displayKarutaQuizAnswerUsecase);
    }
}
