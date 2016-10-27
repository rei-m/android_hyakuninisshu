package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import java.util.Date;

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaQuizRepository;
import me.rei_m.hyakuninisshu.usecase.karuta.AnswerKarutaQuizUsecase;
import rx.Observable;
import rx.functions.Func1;

public class AnswerKarutaQuizUsecaseImpl implements AnswerKarutaQuizUsecase {

    private final KarutaQuizRepository karutaQuizRepository;

    public AnswerKarutaQuizUsecaseImpl(@NonNull KarutaQuizRepository karutaQuizRepository) {
        this.karutaQuizRepository = karutaQuizRepository;
    }

    @Override
    public Observable<Boolean> execute(String quizId, int choiceNo) {
        return karutaQuizRepository.resolve(new KarutaQuizIdentifier(quizId)).concatMap(new Func1<KarutaQuiz, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(KarutaQuiz karutaQuiz) {
                karutaQuiz.verify(choiceNo, new Date());
                karutaQuizRepository.store(karutaQuiz);
                return Observable.just(karutaQuiz.getResult().isCollect);
            }
        });
    }
}
