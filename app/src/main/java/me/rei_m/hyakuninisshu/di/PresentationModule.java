package me.rei_m.hyakuninisshu.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterContact;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterPresenter;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMenuContact;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMenuPresenter;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

@Module
class PresentationModule {

    @Provides
    ActivityNavigator provideActivityNavigator() {
        return new ActivityNavigator();
    }

    @Provides
    TrainingMenuContact.Actions provideTrainingMenuPresenter() {
        return new TrainingMenuPresenter();
    }

    @Provides
    QuizMasterContact.Actions provideQuizMasterPresenter(StartKarutaQuizUsecase startKarutaQuizUsecase,
                                                         DisplayKarutaQuizUsecase displayKarutaQuizUsecase) {
        return new QuizMasterPresenter(startKarutaQuizUsecase, displayKarutaQuizUsecase);
    }
}
