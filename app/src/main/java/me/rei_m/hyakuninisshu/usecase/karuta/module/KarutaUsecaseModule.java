package me.rei_m.hyakuninisshu.usecase.karuta.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayExamUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaExamResultUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizAnswerUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizResultUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialDetailUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.FinishKarutaExamUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.RestartWrongKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaExamUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.StartTrainingForKarutaExamUsecase;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.AnswerKarutaQuizUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.DisplayExamUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.DisplayKarutaExamResultUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.DisplayKarutaQuizAnswerUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.DisplayKarutaQuizResultUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.DisplayKarutaQuizUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.DisplayMaterialDetailUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.DisplayMaterialUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.FinishKarutaExamUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.RestartWrongKarutaQuizUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.StartKarutaExamUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.StartKarutaQuizUsecaseImpl;
import me.rei_m.hyakuninisshu.usecase.karuta.impl.StartTrainingForKarutaExamUsecaseImpl;

@Module
public class KarutaUsecaseModule {

    @Provides
    StartKarutaQuizUsecase provideStartKarutaQuizUsecase(KarutaRepository karutaRepository,
                                                         KarutaQuizRepository karutaQuizRepository,
                                                         KarutaQuizListFactory karutaQuizListFactory) {
        return new StartKarutaQuizUsecaseImpl(karutaRepository, karutaQuizRepository, karutaQuizListFactory);
    }

    @Provides
    RestartWrongKarutaQuizUsecase provideRestartWrongKarutaQuizUsecase(KarutaQuizRepository karutaQuizRepository,
                                                                       KarutaQuizListFactory karutaQuizListFactory) {
        return new RestartWrongKarutaQuizUsecaseImpl(karutaQuizRepository, karutaQuizListFactory);
    }

    @Provides
    DisplayKarutaQuizUsecase provideDisplayKarutaQuizUsecase(KarutaRepository karutaRepository,
                                                             KarutaQuizRepository karutaQuizRepository) {
        return new DisplayKarutaQuizUsecaseImpl(karutaRepository, karutaQuizRepository);
    }

    @Provides
    AnswerKarutaQuizUsecase provideAnswerKarutaQuizUsecase(KarutaQuizRepository karutaQuizRepository) {
        return new AnswerKarutaQuizUsecaseImpl(karutaQuizRepository);
    }

    @Provides
    DisplayKarutaQuizAnswerUsecase provideDisplayKarutaQuizAnswerUsecase(Context context,
                                                                         KarutaRepository karutaRepository,
                                                                         KarutaQuizRepository karutaQuizRepository) {
        return new DisplayKarutaQuizAnswerUsecaseImpl(context, karutaRepository, karutaQuizRepository);
    }

    @Provides
    DisplayKarutaQuizResultUsecase provideDisplayKarutaQuizResultUsecase(Context context,
                                                                         KarutaQuizRepository karutaQuizRepository) {
        return new DisplayKarutaQuizResultUsecaseImpl(context, karutaQuizRepository);
    }

    @Provides
    DisplayExamUsecase provideDisplayExamUsecase(Context context,
                                                 KarutaExamRepository karutaExamRepository) {
        return new DisplayExamUsecaseImpl(context, karutaExamRepository);
    }

    @Provides
    StartKarutaExamUsecase provideStartExamUsecase(KarutaRepository karutaRepository,
                                                   KarutaQuizRepository karutaQuizRepository,
                                                   KarutaQuizListFactory karutaQuizListFactory) {
        return new StartKarutaExamUsecaseImpl(karutaRepository, karutaQuizRepository, karutaQuizListFactory);
    }

    @Provides
    FinishKarutaExamUsecase provideFinishExamUsecase(KarutaQuizRepository karutaQuizRepository,
                                                     KarutaExamRepository karutaExamRepository) {
        return new FinishKarutaExamUsecaseImpl(karutaQuizRepository, karutaExamRepository);
    }

    @Provides
    StartTrainingForKarutaExamUsecase provideStartTrainingForKarutaExamUsecase(KarutaQuizRepository karutaQuizRepository,
                                                                               KarutaQuizListFactory karutaQuizListFactory,
                                                                               KarutaExamRepository KarutaExamRepository) {
        return new StartTrainingForKarutaExamUsecaseImpl(karutaQuizRepository, karutaQuizListFactory, KarutaExamRepository);
    }

    @Provides
    DisplayKarutaExamResultUsecase provideDisplayKarutaExamResultUsecase(Context context,
                                                                         KarutaExamRepository karutaExamRepository) {
        return new DisplayKarutaExamResultUsecaseImpl(context, karutaExamRepository);
    }

    @Provides
    DisplayMaterialUsecase provideDisplayMaterialUsecase(Context context,
                                                         KarutaRepository karutaRepository) {
        return new DisplayMaterialUsecaseImpl(context, karutaRepository);
    }

    @Provides
    DisplayMaterialDetailUsecase provideDisplayMaterialDetailUsecase(Context context,
                                                                     KarutaRepository karutaRepository) {
        return new DisplayMaterialDetailUsecaseImpl(context, karutaRepository);
    }
}
