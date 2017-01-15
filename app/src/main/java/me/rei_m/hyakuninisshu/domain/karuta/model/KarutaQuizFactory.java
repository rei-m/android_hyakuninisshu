package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import java.util.List;

import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaSchema;

public class KarutaQuizFactory {

    private KarutaQuizFactory() {
    }

    public static KarutaQuiz create(@NonNull KarutaQuizSchema schema,
                                    @NonNull List<KarutaIdentifier> choiceList) {

        KarutaQuizIdentifier karutaQuizIdentifier = new KarutaQuizIdentifier(schema.quizId);
        KarutaIdentifier collectIdentifier = new KarutaIdentifier(schema.collectId);
        KarutaQuiz karutaQuiz = new KarutaQuiz(karutaQuizIdentifier,
                choiceList,
                collectIdentifier);
        karutaQuiz.setStartDate(schema.startDate);
        

        return karutaQuiz;
    }
}
