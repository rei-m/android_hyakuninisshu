package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialDetailActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialDetailFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;

@ForActivity
@Subcomponent(modules = {
        ActivityModule.class,
        MaterialDetailFragmentModule.class
})
public interface MaterialDetailActivitySubcomponent extends AndroidInjector<MaterialDetailActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MaterialDetailActivity> {
        public abstract Builder activityModule(ActivityModule module);

        @Override
        public void seedInstance(MaterialDetailActivity instance) {
            activityModule(new ActivityModule(instance));
        }
    }
}
