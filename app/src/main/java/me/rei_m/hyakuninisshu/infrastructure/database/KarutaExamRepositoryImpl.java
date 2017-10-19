package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.NonNull;

import com.github.gfx.android.orma.Inserter;
import com.github.gfx.android.orma.SingleAssociation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamFactory;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamRepository;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizResult;

public class KarutaExamRepositoryImpl implements KarutaExamRepository {

    private final OrmaDatabase orma;

    public KarutaExamRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Single<KarutaExamIdentifier> store(@NonNull List<KarutaQuizResult> karutaQuizResultList,
                                              @NonNull Date tookExamDate) {

        KarutaExamSchema karutaExamSchema = new KarutaExamSchema();

        orma.transactionSync(() -> {

            List<KarutaIdentifier> wrongKarutaIdList = new ArrayList<>();

            long totalAnswerTimeMillSec = 0;

            for (KarutaQuizResult karutaQuizResult : karutaQuizResultList) {
                totalAnswerTimeMillSec += karutaQuizResult.answerTime;
                if (!karutaQuizResult.isCollect) {
                    wrongKarutaIdList.add(karutaQuizResult.collectKarutaId);
                }
            }

            final float averageAnswerTime = totalAnswerTimeMillSec / (float) karutaQuizResultList.size() / (float) TimeUnit.SECONDS.toMillis(1);

            karutaExamSchema.tookExamDate = tookExamDate;
            karutaExamSchema.totalQuizCount = karutaQuizResultList.size();
            karutaExamSchema.averageAnswerTime = averageAnswerTime;
            karutaExamSchema.id = KarutaExamSchema.relation(orma).inserter().execute(karutaExamSchema);

            Inserter<ExamWrongKarutaSchema> examWrongKarutaSchemaInserter = ExamWrongKarutaSchema.relation(orma).inserter();
            Observable.fromIterable(wrongKarutaIdList).subscribe(identifier -> {
                ExamWrongKarutaSchema examWrongKarutaSchema = new ExamWrongKarutaSchema();
                examWrongKarutaSchema.examSchema = SingleAssociation.just(karutaExamSchema);
                examWrongKarutaSchema.karutaId = identifier.value();
                examWrongKarutaSchemaInserter.execute(examWrongKarutaSchema);
            });
        });

        return Single.just(new KarutaExamIdentifier(karutaExamSchema.id));
    }

    @Override
    public Completable adjustHistory(int historySize) {
        Observable<KarutaExamSchema> karutaExamSchemaObservable = KarutaExamSchema.relation(orma)
                .selector()
                .orderByIdAsc()
                .executeAsObservable();

        long examCount = karutaExamSchemaObservable.count().blockingGet();

        if (historySize < examCount) {
            return orma.transactionAsCompletable(() -> {
                KarutaExamSchema_Deleter deleter = KarutaExamSchema.relation(orma).deleter();
                karutaExamSchemaObservable
                        .take(examCount - historySize)
                        .subscribe(karutaExamSchema -> deleter.idEq(karutaExamSchema.id).execute());
            });
        } else {
            return Completable.complete();
        }
    }

    @Override
    public Single<KarutaExam> findBy(@NonNull KarutaExamIdentifier identifier) {
        return KarutaExamSchema.relation(orma)
                .idEq(identifier.value())
                .selector()
                .executeAsObservable()
                .firstOrError()
                .map(examSchema -> {
                    List<ExamWrongKarutaSchema> examWrongKarutaSchemaList = new ArrayList<>();
                    examSchema.getWrongKarutas(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe(examWrongKarutaSchemaList::add);
                    return KarutaExamFactory.create(examSchema, examWrongKarutaSchemaList);
                });
    }

    @Override
    public Single<List<KarutaExam>> list() {
        return KarutaExamSchema.relation(orma)
                .selector()
                .orderByIdDesc()
                .executeAsObservable()
                .map(examSchema -> {
                    List<ExamWrongKarutaSchema> examWrongKarutaSchemaList = new ArrayList<>();
                    examSchema.getWrongKarutas(orma)
                            .selector()
                            .executeAsObservable()
                            .subscribe(examWrongKarutaSchemaList::add);
                    return KarutaExamFactory.create(examSchema, examWrongKarutaSchemaList);
                }).toList();
    }
}
