package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizResultUsecase;

@Module
public class QuizResultFragmentModule {

    private final Context context;

    public QuizResultFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }
    
    @Provides
    QuizResultContact.Actions provideQuizResultPresenter(DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase) {
        return new QuizResultPresenter(displayKarutaQuizResultUsecase);
    }
}
