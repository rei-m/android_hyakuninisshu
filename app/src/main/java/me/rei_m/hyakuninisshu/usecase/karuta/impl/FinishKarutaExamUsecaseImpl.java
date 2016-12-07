package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import me.rei_m.hyakuninisshu.domain.karuta.model.ExamIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizResult;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.FinishKarutaExamUsecase;

public class FinishKarutaExamUsecaseImpl implements FinishKarutaExamUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    private final KarutaExamRepository karutaExamRepository;

    public FinishKarutaExamUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository,
                                       @NonNull KarutaExamRepository karutaExamRepository) {
        this.karutaQuizRepository = karutaQuizRepository;
        this.karutaExamRepository = karutaExamRepository;
    }

    @Override
    public Maybe<Long> execute() {
        return karutaQuizRepository.asEntityList()
                .flatMap(new Function<List<KarutaQuiz>, SingleSource<List<KarutaQuizResult>>>() {
                    @Override
                    public SingleSource<List<KarutaQuizResult>> apply(List<KarutaQuiz> karutaQuizList) throws Exception {
                        return Observable.fromIterable(karutaQuizList).map(KarutaQuiz::getResult).toList();
                    }
                }).flatMapMaybe(karutaQuizResultList -> karutaExamRepository.store(karutaQuizResultList, new Date()))
                .map(ExamIdentifier::getValue);
    }
}
