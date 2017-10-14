package me.rei_m.hyakuninisshu.domain.model.quiz;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface KarutaExamRepository {

    Single<KarutaExamIdentifier> store(List<KarutaQuizResult> karutaQuizResultList, Date tookExamDate);

    Single<KarutaExam> resolve(KarutaExamIdentifier identifier);

    Single<List<KarutaExam>> asEntityList();

    Completable delete(KarutaExamIdentifier identifier);
}
