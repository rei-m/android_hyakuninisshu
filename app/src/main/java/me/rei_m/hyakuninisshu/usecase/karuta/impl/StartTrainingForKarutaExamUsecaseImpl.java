package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizListFactory;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.StartTrainingForKarutaExamUsecase;

public class StartTrainingForKarutaExamUsecaseImpl implements StartTrainingForKarutaExamUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaQuizListFactory karutaQuizListFactory;

    private final KarutaExamRepository karutaExamRepository;

    public StartTrainingForKarutaExamUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository,
                                                 @NonNull KarutaQuizListFactory karutaQuizListFactory,
                                                 @NonNull KarutaExamRepository karutaExamRepository) {
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaQuizListFactory = karutaQuizListFactory;
        this.karutaExamRepository = karutaExamRepository;
    }

    @Override
    public Completable execute() {
        return karutaExamRepository.asEntityList()
                .flatMap(karutaExamList -> Observable.fromIterable(karutaExamList)
                        .reduce(new ArrayList<KarutaIdentifier>(), (karutaIdList, karutaExam) -> {
                            for (KarutaIdentifier wrongKarutaId : karutaExam.wrongKarutaIdList) {
                                if (!karutaIdList.contains(wrongKarutaId)) {
                                    karutaIdList.add(wrongKarutaId);
                                }
                            }
                            return karutaIdList;
                        }))
                .flatMap(karutaQuizListFactory::create)
                .flatMapCompletable(karutaQuizRepository::initialize);
    }
}
