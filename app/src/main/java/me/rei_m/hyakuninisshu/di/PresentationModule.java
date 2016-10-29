package me.rei_m.hyakuninisshu.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizAnswerContact;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizAnswerPresenter;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizContact;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterContact;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterPresenter;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizPresenter;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMenuContact;
import me.rei_m.hyakuninisshu.presentation.karuta.TrainingMenuPresenter;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizAnswerUsecase;
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
    QuizMasterContact.Actions provideQuizMasterPresenter(StartKarutaQuizUsecase startKarutaQuizUsecase) {
        return new QuizMasterPresenter(startKarutaQuizUsecase);
    }

    @Provides
    QuizContact.Actions provideQuizPresenter(DisplayKarutaQuizUsecase displayKarutaQuizUsecase,
                                             AnswerKarutaQuizUsecase answerKarutaQuizUsecase) {
        return new QuizPresenter(displayKarutaQuizUsecase, answerKarutaQuizUsecase);
    }

    @Provides
    QuizAnswerContact.Actions provideQuizAnswerPresenter(DisplayKarutaQuizAnswerUsecase displayKarutaQuizAnswerUsecase) {
        return new QuizAnswerPresenter(displayKarutaQuizAnswerUsecase);
    }
}
