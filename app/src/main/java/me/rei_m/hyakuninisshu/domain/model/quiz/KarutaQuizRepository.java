package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface KarutaQuizRepository {

    Completable initialize(@NonNull KarutaQuizzes karutaQuizzes);

    Single<KarutaQuiz> first();

    Single<KarutaQuiz> findBy(@NonNull KarutaQuizIdentifier identifier);

    Completable store(@NonNull KarutaQuiz karutaQuiz);

    Single<Boolean> existNextQuiz();

    Single<KarutaQuizzes> list();

    Single<KarutaQuizCounter> countQuizByAnswered();
}
