package me.rei_m.hyakuninisshu.di;

import javax.inject.Singleton;

import dagger.Component;
import me.rei_m.hyakuninisshu.App;

@Singleton
@Component(modules = {ApplicationModule.class, DomainModule.class, UsecaseModule.class, InfrastructureModule.class})
public interface ApplicationComponent {

    void inject(App application);

    ActivityComponent activityComponent();
}
