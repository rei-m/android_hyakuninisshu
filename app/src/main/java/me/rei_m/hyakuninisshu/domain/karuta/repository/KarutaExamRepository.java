package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExam;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizResult;

public interface KarutaExamRepository {
    Maybe<KarutaExamIdentifier> store(List<KarutaQuizResult> karutaQuizResultList, Date tookExamDate);

    Single<KarutaExam> resolve(KarutaExamIdentifier identifier);
}
