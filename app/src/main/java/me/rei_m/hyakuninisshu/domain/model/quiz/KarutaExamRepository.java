package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface KarutaExamRepository {

    Single<KarutaExamIdentifier> storeResult(@NonNull KarutaExamResult karutaExamResult, @NonNull Date tookExamDate);

    Completable adjustHistory(int historySize);

    Single<KarutaExam> findBy(@NonNull KarutaExamIdentifier identifier);

    Single<KarutaExams> list();
}
