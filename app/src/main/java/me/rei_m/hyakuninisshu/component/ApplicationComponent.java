package me.rei_m.hyakuninisshu.component;

import dagger.Component;
import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.domain.karuta.module.KarutaDomainModule;
import me.rei_m.hyakuninisshu.infrastructure.module.InfrastructureModule;
import me.rei_m.hyakuninisshu.module.ApplicationModule;
import me.rei_m.hyakuninisshu.module.ForApplication;
import me.rei_m.hyakuninisshu.presentation.component.EntranceActivityComponent;
import me.rei_m.hyakuninisshu.presentation.component.SplashActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.ExamMasterActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.MaterialDetailActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.MaterialEditActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.MaterialSingleActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.QuizMasterActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.component.TrainingExamMasterActivityComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.module.ExamMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialDetailActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialEditActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.MaterialSingleActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.QuizMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.karuta.module.TrainingExamMasterActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.ActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.EntranceActivityModule;
import me.rei_m.hyakuninisshu.presentation.module.SplashActivityModule;

@ForApplication
@Component(modules = {ApplicationModule.class,
        InfrastructureModule.class,
        KarutaDomainModule.class})
public interface ApplicationComponent {

    void inject(App application);

    SplashActivityComponent plus(ActivityModule activityModule, SplashActivityModule splashActivityModule);

    EntranceActivityComponent plus(ActivityModule activityModule, EntranceActivityModule entranceActivityModule);

    QuizMasterActivityComponent plus(ActivityModule activityModule, QuizMasterActivityModule quizMasterActivityModule);

    ExamMasterActivityComponent plus(ActivityModule activityModule, ExamMasterActivityModule examMasterActivityModule);

    TrainingExamMasterActivityComponent plus(ActivityModule activityModule, TrainingExamMasterActivityModule trainingExamMasterActivityModule);

    MaterialDetailActivityComponent plus(ActivityModule activityModule, MaterialDetailActivityModule materialDetailActivityModule);

    MaterialSingleActivityComponent plus(ActivityModule activityModule, MaterialSingleActivityModule materialSingleActivityModule);

    MaterialEditActivityComponent plus(ActivityModule activityModule, MaterialEditActivityModule materialEditActivityModule);
}
