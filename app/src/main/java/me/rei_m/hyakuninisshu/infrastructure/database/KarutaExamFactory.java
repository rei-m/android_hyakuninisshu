package me.rei_m.hyakuninisshu.infrastructure.database;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExam;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamResult;
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizResultSummary;

public class KarutaExamFactory {

    private KarutaExamFactory() {
    }

    public static KarutaExam create(@NonNull KarutaExamSchema schema,
                                    @NonNull List<ExamWrongKarutaSchema> examWrongKarutaSchemaList) {

        KarutaExamIdentifier identifier = new KarutaExamIdentifier(schema.id);

        List<KarutaIdentifier> wrongKarutaIdentifierList = new ArrayList<>();
        Observable.fromIterable(examWrongKarutaSchemaList)
                .map(examWrongKarutaSchema -> new KarutaIdentifier(examWrongKarutaSchema.karutaId))
                .subscribe(wrongKarutaIdentifierList::add);

        KarutaIds wrongKarutaIds = new KarutaIds(wrongKarutaIdentifierList);

        KarutaQuizResultSummary resultSummary = new KarutaQuizResultSummary(
                schema.totalQuizCount,
                schema.totalQuizCount - wrongKarutaIds.size(),
                schema.averageAnswerTime
        );

        KarutaExamResult result = new KarutaExamResult(resultSummary, wrongKarutaIds);

        return new KarutaExam(identifier, result);
    }
}
