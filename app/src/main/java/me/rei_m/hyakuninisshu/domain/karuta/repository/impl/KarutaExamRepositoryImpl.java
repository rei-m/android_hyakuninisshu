package me.rei_m.hyakuninisshu.domain.karuta.repository.impl;

import android.support.annotation.NonNull;

import com.github.gfx.android.orma.Inserter;
import com.github.gfx.android.orma.SingleAssociation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.karuta.model.ExamIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizResult;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaExamRepository;
import me.rei_m.hyakuninisshu.infrastructure.database.ExamSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.ExamWrongKarutaSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.OrmaDatabase;

public class KarutaExamRepositoryImpl implements KarutaExamRepository {

    private final OrmaDatabase orma;

    public KarutaExamRepositoryImpl(@NonNull OrmaDatabase orma) {
        this.orma = orma;
    }

    @Override
    public Maybe<ExamIdentifier> store(@NonNull List<KarutaQuizResult> karutaQuizResultList,
                                       @NonNull Date tookExamDate) {

        ExamSchema examSchema = new ExamSchema();

        orma.transactionSync(() -> {

            List<KarutaIdentifier> wrongKarutaIdList = new ArrayList<>();

            Observable.fromIterable(karutaQuizResultList)
                    .filter(karutaQuizResult -> !karutaQuizResult.isCollect)
                    .subscribe(karutaQuizResult -> {
                        wrongKarutaIdList.add(karutaQuizResult.collectKarutaId);
                    });

            examSchema.tookExamDate = tookExamDate;
            examSchema.totalQuizCount = karutaQuizResultList.size();
            examSchema.id = ExamSchema.relation(orma).inserter().execute(examSchema);

            Inserter<ExamWrongKarutaSchema> examWrongKarutaSchemaInserter = ExamWrongKarutaSchema.relation(orma).inserter();
            Observable.fromIterable(wrongKarutaIdList).subscribe(identifier -> {
                ExamWrongKarutaSchema examWrongKarutaSchema = new ExamWrongKarutaSchema();
                examWrongKarutaSchema.examSchema = SingleAssociation.just(examSchema);
                examWrongKarutaSchema.karutaId = identifier.getValue();
                examWrongKarutaSchemaInserter.execute(examWrongKarutaSchema);
            });
        });

        return Maybe.just(new ExamIdentifier(examSchema.id));
    }
}
