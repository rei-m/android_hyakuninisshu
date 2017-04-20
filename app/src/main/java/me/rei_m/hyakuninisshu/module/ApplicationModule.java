package me.rei_m.hyakuninisshu.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.model.ApplicationModel;
import me.rei_m.hyakuninisshu.model.KarutaExamModel;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.model.KarutaQuizModel;
import me.rei_m.hyakuninisshu.model.KarutaTrainingModel;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(@NonNull Application application) {
        this.context = application.getApplicationContext();
    }

    @Provides
    @ForApplication
    Context provideContext() {
        return context;
    }

    @Provides
    @ForApplication
    ApplicationModel provideApplicationModel(KarutaRepository karutaRepository) {
        return new ApplicationModel(karutaRepository);
    }

    @Provides
    @ForApplication
    KarutaTrainingModel provideKarutaTrainingModel(KarutaRepository karutaRepository,
                                                   KarutaQuizRepository karutaQuizRepository,
                                                   KarutaQuizListFactory karutaQuizListFactory,
                                                   KarutaExamRepository karutaExamRepository) {
        return new KarutaTrainingModel(karutaRepository,
                karutaQuizRepository,
                karutaQuizListFactory,
                karutaExamRepository);
    }

    @Provides
    @ForApplication
    KarutaQuizModel provideKarutaQuizModel(KarutaRepository karutaRepository,
                                           KarutaQuizRepository karutaQuizRepository) {
        return new KarutaQuizModel(karutaRepository, karutaQuizRepository);
    }

    @Provides
    @ForApplication
    KarutaModel provideKarutaModel(KarutaRepository karutaRepository) {
        return new KarutaModel(karutaRepository);
    }

    @Provides
    @ForApplication
    KarutaExamModel provideKarutaExamModel(KarutaRepository karutaRepository,
                                           KarutaQuizRepository karutaQuizRepository,
                                           KarutaQuizListFactory karutaQuizListFactory,
                                           KarutaExamRepository karutaExamRepository) {
        return new KarutaExamModel(karutaRepository, karutaQuizRepository, karutaQuizListFactory, karutaExamRepository);
    }
}
