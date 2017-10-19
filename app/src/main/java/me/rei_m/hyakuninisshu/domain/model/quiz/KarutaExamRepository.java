package me.rei_m.hyakuninisshu.domain.model.quiz;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface KarutaExamRepository {

    Single<KarutaExamIdentifier> store(List<KarutaQuizResult> karutaQuizResultList, Date tookExamDate);

    Completable adjustHistory(int historySize);

    Single<KarutaExam> findBy(KarutaExamIdentifier identifier);

    Single<List<KarutaExam>> list();
}
