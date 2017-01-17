package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.Date;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;

public class AnswerKarutaQuizUsecaseImpl implements AnswerKarutaQuizUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    public AnswerKarutaQuizUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Single<Boolean> execute(String quizId, int choiceNo) {
        return karutaQuizRepository.resolve(new KarutaQuizIdentifier(quizId)).flatMap(new Function<KarutaQuiz, SingleSource<Boolean>>() {
            @Override
            public SingleSource<Boolean> apply(KarutaQuiz karutaQuiz) {
                karutaQuiz.verify(choiceNo, new Date());
                return karutaQuizRepository.store(karutaQuiz)
                        .toSingleDefault(karutaQuiz.getResult().isCollect);
            }
        });
    }
}
