package me.rei_m.hyakuninisshu.presentation.karuta.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.karuta.MaterialSingleActivity;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.MaterialDetailFragmentModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;

@ForActivity
@Subcomponent(modules = {
        ActivityModule.class,
        MaterialDetailFragmentModule.class
})
public interface MaterialSingleActivitySubcomponent extends AndroidInjector<MaterialSingleActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MaterialSingleActivity> {
        public abstract Builder activityModule(ActivityModule module);

        @Override
        public void seedInstance(MaterialSingleActivity instance) {
            activityModule(new ActivityModule(instance));
        }
    }
}
