package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;
import rx.Observable;
import rx.functions.Func1;

public class StartKarutaQuizUsecaseImpl implements StartKarutaQuizUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaQuizListFactory karutaQuizListFactory;

    public StartKarutaQuizUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository,
                                      @NonNull KarutaQuizListFactory karutaQuizListFactory) {
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
    }

    @Override
    public Observable<Void> execute(int fromKarutaId, int toKarutaId, int kimarijiPosition) {
        return karutaQuizListFactory.create(fromKarutaId, toKarutaId, kimarijiPosition).concatMap(new Func1<List<KarutaQuiz>, Observable<Void>>() {
            @Override
            public Observable<Void> call(List<KarutaQuiz> karutaQuizList) {
                if (karutaQuizList.isEmpty()) {
                    return Observable.error(new IllegalArgumentException("This condition is not valid."));
                } else {
                    return karutaQuizRepository.initialize(karutaQuizList);
                }
            }
        });
    }
}
