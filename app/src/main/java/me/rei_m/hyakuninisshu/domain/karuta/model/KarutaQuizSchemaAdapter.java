package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizSchema;

public class KarutaQuizSchemaAdapter {

    private KarutaQuizSchemaAdapter() {
    }

    public static KarutaQuizSchema convert(@NonNull KarutaQuiz karutaQuiz) {
        KarutaQuizSchema karutaQuizSchema = new KarutaQuizSchema();
        karutaQuizSchema.quizId = karutaQuiz.getIdentifier().getValue();
        karutaQuizSchema.collectId = karutaQuiz.contents.collectId.getValue();
        karutaQuizSchema.startDate = karutaQuiz.getStartDate();
        if (karutaQuiz.getResult() != null) {
            karutaQuizSchema.answerTime = karutaQuiz.getResult().answerTime;
            karutaQuizSchema.isCollect = karutaQuiz.getResult().isCollect;
        }
        return karutaQuizSchema;
    }
}
