package me.rei_m.hyakuninisshu.di;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.presentation.ActivityNavigator;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizAnswerPresenter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizContact;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterContact;
import me.rei_m.hyakuninisshu.presentation.karuta.QuizMasterPresenter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizPresenter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.QuizResultPresenter;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuContact;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.TrainingMenuPresenter;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizAnswerUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizResultUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

@Module
public class PresentationModule {

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

    @Provides
    QuizResultContact.Actions provideQuizResultPresenter(DisplayKarutaQuizResultUsecase displayKarutaQuizResultUsecase) {
        return new QuizResultPresenter(displayKarutaQuizResultUsecase);
    }
}
