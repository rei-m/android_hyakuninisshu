package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;

public class KarutaQuizzes {

    private final List<KarutaQuiz> values;

    public KarutaQuizzes(@NonNull List<KarutaQuiz> values) {
        this.values = values;
    }

    public List<KarutaQuiz> asList() {
        return Collections.unmodifiableList(values);
    }

    public KarutaIds wrongKarutaIds() {
        return Observable.fromIterable(values).filter(karutaQuiz -> karutaQuiz.result() != null && !karutaQuiz.result().isCollect)
                .map(karutaQuiz -> karutaQuiz.result().collectKarutaId)
                .toList()
                .map(KarutaIds::new)
                .blockingGet();
    }

    public TrainingResult resultSummary() throws IllegalStateException {
        final int quizCount = values.size();

        long totalAnswerTimeMillSec = 0;

        int collectCount = 0;

        for (KarutaQuiz karutaQuiz : values) {
            if (karutaQuiz.result() == null) {
                throw new IllegalStateException("Training is not finished.");
            }

            totalAnswerTimeMillSec += karutaQuiz.result().answerTime;
            if (karutaQuiz.result().isCollect) {
                collectCount++;
            }
        }

        final float averageAnswerTime = totalAnswerTimeMillSec / (float) quizCount / (float) TimeUnit.SECONDS.toMillis(1);

        final boolean canRestartTraining = collectCount != quizCount;

        return new TrainingResult(quizCount, collectCount, averageAnswerTime, canRestartTraining);
    }

    public List<KarutaQuizResult> results() {
        return Observable.fromIterable(values).map(KarutaQuiz::result).toList().blockingGet();
    }
}
