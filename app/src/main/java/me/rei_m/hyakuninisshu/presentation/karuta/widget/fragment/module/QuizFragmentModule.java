package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;

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
    QuizContact.Actions provideQuizPresenter(DisplayKarutaQuizUsecase displayKarutaQuizUsecase,
                                             AnswerKarutaQuizUsecase answerKarutaQuizUsecase) {
        return new QuizPresenter(displayKarutaQuizUsecase, answerKarutaQuizUsecase);
    }
}
