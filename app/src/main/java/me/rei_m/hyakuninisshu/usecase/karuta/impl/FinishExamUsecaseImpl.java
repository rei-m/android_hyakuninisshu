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
import me.rei_m.hyakuninisshu.domain.karuta.repository.ExamRepository;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.FinishExamUsecase;

public class FinishExamUsecaseImpl implements FinishExamUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    private final ExamRepository examRepository;

    public FinishExamUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository,
                                 @NonNull ExamRepository examRepository) {
        this.karutaQuizRepository = karutaQuizRepository;
        this.examRepository = examRepository;
    }

    @Override
    public Maybe<ExamIdentifier> execute() {
        return karutaQuizRepository.asEntityList()
                .flatMap(new Function<List<KarutaQuiz>, SingleSource<List<KarutaQuizResult>>>() {
                    @Override
                    public SingleSource<List<KarutaQuizResult>> apply(List<KarutaQuiz> karutaQuizList) throws Exception {
                        return Observable.fromIterable(karutaQuizList).map(KarutaQuiz::getResult).toList();
                    }
                })
                .flatMapMaybe(karutaQuizResultList -> {
                    System.out.println("karutaQuizResultList");

                    return examRepository.store(karutaQuizResultList, new Date());});
    }
}
