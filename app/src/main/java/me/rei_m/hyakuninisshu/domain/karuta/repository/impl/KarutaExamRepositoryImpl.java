package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import android.support.annotation.NonNull;

import com.github.gfx.android.orma.Inserter;
import com.github.gfx.android.orma.SingleAssociation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.model.ExamIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExam;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaExamFactory;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizResult;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.infrastructure.database.ExamWrongKarutaSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaExamSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

public class KarutaExamRepositoryImpl implements KarutaExamRepository {

    private final OrmaDatabase orma;

    public KarutaExamRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Maybe<ExamIdentifier> store(@NonNull List<KarutaQuizResult> karutaQuizResultList,
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
                examWrongKarutaSchema.karutaId = identifier.getValue();
                examWrongKarutaSchemaInserter.execute(examWrongKarutaSchema);
            });
        });

        return Maybe.just(new ExamIdentifier(karutaExamSchema.id));
    }

    @Override
    public Single<KarutaExam> resolve(@NonNull ExamIdentifier identifier) {
        return KarutaExamSchema.relation(orma)
                .idEq(identifier.getValue())
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
}
