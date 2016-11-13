package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

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
        return karutaQuizListFactory.create(fromKarutaId, toKarutaId, kimarijiPosition).concatMap(new Function<List<KarutaQuiz>, ObservableSource<Void>>() {
            @Override
            public ObservableSource<Void> apply(List<KarutaQuiz> karutaQuizList) {
                if (karutaQuizList.isEmpty()) {
                    return Observable.error(new IllegalArgumentException("This condition is not valid."));
                } else {
                    return karutaQuizRepository.initialize(karutaQuizList);
                }
            }
        });
    }
}
