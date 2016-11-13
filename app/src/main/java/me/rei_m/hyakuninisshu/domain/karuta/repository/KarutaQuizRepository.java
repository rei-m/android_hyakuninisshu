package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;

public interface KarutaQuizRepository {

    Observable<Void> initialize(List<KarutaQuiz> karutaQuizList);

    Observable<KarutaQuiz> pop();

    Observable<KarutaQuiz> resolve(KarutaQuizIdentifier identifier);

    Observable<Void> store(KarutaQuiz karutaQuiz);

    Observable<Boolean> existNextQuiz();

    Observable<List<KarutaQuiz>> asEntityList();
}
