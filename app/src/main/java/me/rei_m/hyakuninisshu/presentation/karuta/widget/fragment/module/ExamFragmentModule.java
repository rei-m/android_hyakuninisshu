package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamPresenter;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;

@Module
public class ExamFragmentModule {
    private final Context context;

    public ExamFragmentModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForFragment
    Context provideContext() {
        return context;
    }

    @Provides
    ExamContact.Actions provideQuizAnswerPresenter() {
        return new ExamPresenter();
    }
}
