package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;

public interface KarutaQuizRepository {

    Observable<Void> initialize(List<KarutaQuiz> karutaQuizList);

    Maybe<KarutaQuiz> pop();

    Maybe<KarutaQuiz> resolve(KarutaQuizIdentifier identifier);

    Observable<Void> store(KarutaQuiz karutaQuiz);

    Single<Boolean> existNextQuiz();

    Observable<List<KarutaQuiz>> asEntityList();
}
