package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExam;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
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
    public Single<Long> execute() {
        return karutaQuizRepository.asEntityList()
                .flatMap(karutaQuizList -> Observable.fromIterable(karutaQuizList).map(KarutaQuiz::getResult).toList())
                .flatMap(karutaQuizResultList -> karutaExamRepository.store(karutaQuizResultList, new Date()))
                .flatMap(karutaExamIdentifier -> {
                    List<KarutaExam> karutaExamList = karutaExamRepository.asEntityList().blockingGet();
                    int currentExamCount = karutaExamList.size();
                    if (KarutaExam.MAX_HISTORY_COUNT < currentExamCount) {
                        for (KarutaExam karutaExam : karutaExamList.subList(KarutaExam.MAX_HISTORY_COUNT - 1, currentExamCount - 1)) {
                            karutaExamRepository.delete(karutaExam.getIdentifier()).subscribe();
                        }
                    }
                    return Single.just(karutaExamIdentifier.getValue());
                });
    }
}
