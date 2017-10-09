package me.rei_m.hyakuninisshu.presentation.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.rei_m.hyakuninisshu.presentation.SplashActivity;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ForActivity;

@ForActivity
@Subcomponent(modules = {ActivityModule.class})
public interface SplashActivitySubcomponent extends AndroidInjector<SplashActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SplashActivity> {

        public abstract Builder activityModule(ActivityModule module);

        @Override
        public void seedInstance(SplashActivity instance) {
            activityModule(new ActivityModule(instance));
        }
    }
}
