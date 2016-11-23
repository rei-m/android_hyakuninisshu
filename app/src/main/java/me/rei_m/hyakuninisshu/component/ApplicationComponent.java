package me.rei_m.hyakuninisshu.component;

import javax.inject.Singleton;

import dagger.Component;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.domain.karuta.module.KarutaDomainModule;
import me.rei_m.hyakuninisshu.infrastructure.module.InfrastructureModule;
import me.rei_m.hyakuninisshu.module.ApplicationModule;
import me.rei_m.hyakuninisshu.presentation.karuta.component.EntranceActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.QuizMasterActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.SplashActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.module.EntranceActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.QuizMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.SplashActivityModule;
import me.rei_m.hyakuninisshu.usecase.karuta.module.KarutaUsecaseModule;

@Singleton
@Component(modules = {ApplicationModule.class, KarutaDomainModule.class, KarutaUsecaseModule.class, InfrastructureModule.class})
public interface ApplicationComponent {

    void inject(App application);

    SplashActivityComponent plus(SplashActivityModule activityModule);

    QuizMasterActivityComponent plus(QuizMasterActivityModule activityModule);

    EntranceActivityComponent plus(EntranceActivityModule activityModule);
}
