package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaExamUsecase;

public class StartKarutaExamUsecaseImpl implements StartKarutaExamUsecase {

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaQuizListFactory karutaQuizListFactory;

    public StartKarutaExamUsecaseImpl(@NonNull KarutaRepository karutaRepository,
                                      @NonNull KarutaQuizRepository karutaQuizRepository,
                                      @NonNull KarutaQuizListFactory karutaQuizListFactory) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
    }

    @Override
    public Completable execute() {
        return karutaRepository.asEntityList()
                .flatMap(karutaList -> Observable.fromIterable(karutaList).map(AbstractEntity::getIdentifier).toList())
                .flatMap(karutaQuizListFactory::create)
                .flatMapCompletable(karutaQuizList -> {
                    List<KarutaQuiz> finallyKarutaQuizList = new ArrayList<>();
                    for (int targetIndex : ArrayUtil.generateRandomArray(karutaQuizList.size(), karutaQuizList.size())) {
                        finallyKarutaQuizList.add(karutaQuizList.get(targetIndex));
                    }
                    return karutaQuizRepository.initialize(finallyKarutaQuizList);
                });
    }
}
