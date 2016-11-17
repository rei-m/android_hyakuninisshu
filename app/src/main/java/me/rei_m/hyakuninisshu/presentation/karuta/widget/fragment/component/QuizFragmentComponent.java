package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizFragment;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizFragmentModule;

@Subcomponent(modules = {FragmentModule.class, QuizFragmentModule.class})
public interface QuizFragmentComponent {
    void inject(QuizFragment fragment);
}
