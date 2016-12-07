package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.infrastructure.database.ExamSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.ExamWrongKarutaSchema;

public class KarutaExamFactory {

    private KarutaExamFactory() {
    }

    public static Exam create(@NonNull ExamSchema schema,
                              @NonNull List<ExamWrongKarutaSchema> examWrongKarutaSchemaList) {

        ExamIdentifier identifier = new ExamIdentifier(schema.id);

        List<KarutaIdentifier> wrongKarutaIdentifierList = new ArrayList<>();
        Observable.fromIterable(examWrongKarutaSchemaList).subscribe(examWrongKarutaSchema -> {
            wrongKarutaIdentifierList.add(new KarutaIdentifier(examWrongKarutaSchema.karutaId));
        });

        return new Exam(identifier, schema.tookExamDate, schema.totalQuizCount, wrongKarutaIdentifierList);
    }
}
