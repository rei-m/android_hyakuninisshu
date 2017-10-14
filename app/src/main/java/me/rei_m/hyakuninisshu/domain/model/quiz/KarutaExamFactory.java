package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.infrastructure.database.ExamWrongKarutaSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaExamSchema;

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

        return new KarutaExam(identifier,
                schema.tookExamDate,
                schema.totalQuizCount,
                schema.averageAnswerTime,
                wrongKarutaIdentifierList);
    }
}
