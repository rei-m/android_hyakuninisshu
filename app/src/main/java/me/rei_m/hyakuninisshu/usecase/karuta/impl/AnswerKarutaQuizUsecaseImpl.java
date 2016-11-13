package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.Date;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
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
    public Maybe<Boolean> execute(String quizId, int choiceNo) {
        return karutaQuizRepository.resolve(new KarutaQuizIdentifier(quizId)).concatMap(new Function<KarutaQuiz, MaybeSource<Boolean>>() {
            @Override
            public MaybeSource<Boolean> apply(KarutaQuiz karutaQuiz) {
                karutaQuiz.verify(choiceNo, new Date());
                karutaQuizRepository.store(karutaQuiz);
                return Maybe.just(karutaQuiz.getResult().isCollect);
            }
        });
    }
}
