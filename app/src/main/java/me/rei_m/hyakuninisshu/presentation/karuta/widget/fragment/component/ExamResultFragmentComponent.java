package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component;

import dagger.Subcomponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.ExamResultFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.ExamResultFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.FragmentModule;

@Subcomponent(modules = {FragmentModule.class, ExamResultFragmentModule.class})
public interface ExamResultFragmentComponent {
    void inject(ExamResultFragment fragment);
}
