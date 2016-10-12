package me.rei_m.hyakuninisshu.domain.karuta.repository;

import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuiz;
import rx.Observable;

public interface KarutaQuizRepository {

    Observable<Void> initializeEntityList(List<KarutaQuiz> karutaQuizList);

    Observable<List<KarutaQuiz>> asEntityList();
}
