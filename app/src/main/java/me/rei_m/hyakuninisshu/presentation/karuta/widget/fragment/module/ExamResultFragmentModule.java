package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamResultContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamResultPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaExamResultUsecase;

@Module
public class ExamResultFragmentModule {

    private final Context context;

    public ExamResultFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    ExamResultContact.Actions provideQuizResultPresenter(DisplayKarutaExamResultUsecase displayKarutaExamResultUsecase) {
        return new ExamResultPresenter(displayKarutaExamResultUsecase);
    }
}
