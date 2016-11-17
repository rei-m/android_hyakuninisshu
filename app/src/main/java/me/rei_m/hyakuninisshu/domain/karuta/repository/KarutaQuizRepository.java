package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;

public interface KarutaQuizRepository {

    Completable initialize(List<KarutaQuiz> karutaQuizList);

    Maybe<KarutaQuiz> pop();

    Maybe<KarutaQuiz> resolve(KarutaQuizIdentifier identifier);

    Completable store(KarutaQuiz karutaQuiz);

    Single<Boolean> existNextQuiz();

    Single<List<KarutaQuiz>> asEntityList();
}
