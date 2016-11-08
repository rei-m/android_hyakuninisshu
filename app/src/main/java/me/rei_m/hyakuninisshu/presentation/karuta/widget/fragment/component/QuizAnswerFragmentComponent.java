package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerFragment;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizAnswerFragmentModule;

@Subcomponent(modules = {FragmentModule.class, QuizAnswerFragmentModule.class})
public interface QuizAnswerFragmentComponent {
    void inject(QuizAnswerFragment fragment);
}
