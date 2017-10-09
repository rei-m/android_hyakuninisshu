package me.rei_m.hyakuninisshu.component;

import javax.inject.Singleton;

import dagger.Component;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.domain.karuta.module.KarutaDomainModule;
import me.rei_m.hyakuninisshu.infrastructure.module.InfrastructureModule;
import me.rei_m.hyakuninisshu.module.ApplicationModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.ExamMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialDetailActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialEditActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialSingleActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.TrainingExamMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.TrainingMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.EntranceActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.SplashActivityModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        InfrastructureModule.class,
        KarutaDomainModule.class,
        SplashActivityModule.class,
        EntranceActivityModule.class,
        ExamMasterActivityModule.class,
        MaterialDetailActivityModule.class,
        MaterialEditActivityModule.class,
        MaterialSingleActivityModule.class,
        TrainingExamMasterActivityModule.class,
        TrainingMasterActivityModule.class
})
public interface ApplicationComponent {
    void inject(App application);
}
