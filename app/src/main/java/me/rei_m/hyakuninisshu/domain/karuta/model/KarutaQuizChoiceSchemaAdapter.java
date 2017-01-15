package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import com.github.gfx.android.orma.SingleAssociation;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizChoiceSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizSchema;

public class KarutaQuizChoiceSchemaAdapter {

    private KarutaQuizChoiceSchemaAdapter() {
    }

    public static List<KarutaQuizChoiceSchema> convert(@NonNull KarutaQuiz karutaQuiz,
                                                       KarutaQuizSchema karutaQuizSchema) {

        List<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaList = new ArrayList<>();
        for (KarutaIdentifier karutaIdentifier : karutaQuiz.contents.choiceList) {
            KarutaQuizChoiceSchema karutaQuizChoiceSchema = new KarutaQuizChoiceSchema();
            karutaQuizChoiceSchema.karutaQuizSchema = SingleAssociation.just(karutaQuizSchema);
            karutaQuizChoiceSchema.karutaId = karutaIdentifier.getValue();
            karutaQuizChoiceSchemaList.add(karutaQuizChoiceSchema);
        }
        return karutaQuizChoiceSchemaList;
    }
}
