package me.rei_m.hyakuninisshu.presentation.support.widget.fragment.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.module.ForFragment;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.SupportFragment;
import me.rei_m.hyakuninisshu.presentation.support.widget.fragment.module.SupportFragmentModule;

@ForFragment
@Subcomponent(modules = {SupportFragmentModule.class})
public interface SupportFragmentComponent extends AndroidInjector<SupportFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SupportFragment> {
    }
}
