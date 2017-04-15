package me.rei_m.hyakuninisshu.domain.karuta.repository;

import android.support.v4.util.Pair;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;

public interface KarutaQuizRepository {

    Completable initialize(List<KarutaQuiz> karutaQuizList);

    Single<KarutaQuiz> pop();

    Single<KarutaQuiz> resolve(KarutaQuizIdentifier identifier);

    Completable store(KarutaQuiz karutaQuiz);

    Single<Boolean> existNextQuiz();

    Single<List<KarutaQuiz>> asEntityList();
    
    Single<Pair<Integer, Integer>> countQuizByAnswered();
}
