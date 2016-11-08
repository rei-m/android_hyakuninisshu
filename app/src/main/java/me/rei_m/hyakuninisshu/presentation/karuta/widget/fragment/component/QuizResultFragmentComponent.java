package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultFragment;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizResultFragmentModule;

@Subcomponent(modules = {FragmentModule.class, QuizResultFragmentModule.class})
public interface QuizResultFragmentComponent {
    void inject(QuizResultFragment fragment);
}
