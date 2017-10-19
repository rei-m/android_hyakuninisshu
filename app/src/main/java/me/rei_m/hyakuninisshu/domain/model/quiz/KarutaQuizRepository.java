package me.rei_m.hyakuninisshu.domain.model.quiz;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface KarutaQuizRepository {

    Completable initialize(List<KarutaQuiz> karutaQuizList);

    Single<KarutaQuiz> pop();

    Single<KarutaQuiz> resolve(KarutaQuizIdentifier identifier);

    Completable store(KarutaQuiz karutaQuiz);

    Single<Boolean> existNextQuiz();

    Single<KarutaQuizzes> list();

    Single<KarutaQuizPosition> countQuizByAnswered();
}
