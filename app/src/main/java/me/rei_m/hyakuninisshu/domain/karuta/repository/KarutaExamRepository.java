package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.Exam;
import me.rei_m.hyakuninisshu.domain.karuta.model.ExamIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizResult;

public interface KarutaExamRepository {
    Maybe<ExamIdentifier> store(List<KarutaQuizResult> karutaQuizResultList, Date tookExamDate);

    Single<Exam> resolve(ExamIdentifier identifier);
}
