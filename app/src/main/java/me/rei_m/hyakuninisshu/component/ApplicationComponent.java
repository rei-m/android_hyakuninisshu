package me.rei_m.hyakuninisshu.component;

import dagger.Component;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.domain.karuta.module.KarutaDomainModule;
import me.rei_m.hyakuninisshu.infrastructure.module.InfrastructureModule;
import me.rei_m.hyakuninisshu.module.ApplicationModule;
import me.rei_m.hyakuninisshu.module.ForApplication;
import me.rei_m.hyakuninisshu.presentation.component.EntranceActivityComponent;
import me.rei_m.hyakuninisshu.presentation.component.SplashActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.QuizMasterActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.module.QuizMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.EntranceActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.SplashActivityModule;
import me.rei_m.hyakuninisshu.usecase.karuta.module.KarutaUsecaseModule;
import me.rei_m.hyakuninisshu.usecase.module.UsecaseModule;

@ForApplication
@Component(modules = {ApplicationModule.class,
        InfrastructureModule.class,
        KarutaDomainModule.class,
        UsecaseModule.class,
        KarutaUsecaseModule.class})
public interface ApplicationComponent {

    void inject(App application);

    SplashActivityComponent plus(SplashActivityModule activityModule);

    QuizMasterActivityComponent plus(QuizMasterActivityModule activityModule);

    EntranceActivityComponent plus(EntranceActivityModule activityModule);
}
