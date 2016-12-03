package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;
import me.rei_m.hyakuninisshu.usecase.karuta.StartKarutaQuizUsecase;

public class StartKarutaQuizUsecaseImpl implements StartKarutaQuizUsecase {

    private final KarutaRepository karutaRepository;

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaQuizListFactory karutaQuizListFactory;

    public StartKarutaQuizUsecaseImpl(@NonNull KarutaRepository karutaRepository,
                                      @NonNull KarutaQuizRepository karutaQuizRepository,
                                      @NonNull KarutaQuizListFactory karutaQuizListFactory) {
        this.karutaRepository = karutaRepository;
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
    }

    @Override
    public Completable execute(int fromKarutaId,
                               int toKarutaId,
                               int kimarijiPosition) {

        int quizSize = toKarutaId - fromKarutaId + 1;

        Single<List<Karuta>> karutaListObservable = (kimarijiPosition == 0) ?
                karutaRepository.asEntityList(new KarutaIdentifier(fromKarutaId), new KarutaIdentifier(toKarutaId)) :
                karutaRepository.asEntityList(new KarutaIdentifier(fromKarutaId), new KarutaIdentifier(toKarutaId), kimarijiPosition);

        return karutaListObservable.map(karutaList -> {
            List<KarutaIdentifier> correctKarutaIdList = new ArrayList<>();
            int size = (karutaList.size() < quizSize) ? karutaList.size() : quizSize;
            int[] collectKarutaIndexList = ArrayUtil.generateRandomArray(karutaList.size(), size);
            for (int i : collectKarutaIndexList) {
                correctKarutaIdList.add(karutaList.get(i).getIdentifier());
            }
            return correctKarutaIdList;
        }).flatMap(karutaQuizListFactory::create).flatMapCompletable(karutaQuizList -> {
            if (karutaQuizList.isEmpty()) {
                return Completable.error(new IllegalArgumentException("This condition is not valid."));
            } else {
                return karutaQuizRepository.initialize(karutaQuizList);
            }
        });
    }
}
