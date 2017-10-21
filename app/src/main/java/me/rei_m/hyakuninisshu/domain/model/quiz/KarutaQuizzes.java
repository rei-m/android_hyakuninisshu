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

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public KarutaIds wrongKarutaIds() {
        return Observable.fromIterable(values).filter(karutaQuiz -> karutaQuiz.result() != null && !karutaQuiz.result().isCorrect())
                .map(karutaQuiz -> karutaQuiz.result().collectKarutaId())
                .toList()
                .map(KarutaIds::new)
                .blockingGet();
    }

    public KarutaQuizResultSummary resultSummary() throws IllegalStateException {
        final int quizCount = values.size();

        long totalAnswerTimeMillSec = 0;

        int collectCount = 0;

        for (KarutaQuiz karutaQuiz : values) {
            if (karutaQuiz.result() == null) {
                throw new IllegalStateException("Training is not finished.");
            }

            totalAnswerTimeMillSec += karutaQuiz.result().answerTime();
            if (karutaQuiz.result().isCorrect()) {
                collectCount++;
            }
        }

        final float averageAnswerTime = totalAnswerTimeMillSec / (float) quizCount / (float) TimeUnit.SECONDS.toMillis(1);

        return new KarutaQuizResultSummary(quizCount, collectCount, averageAnswerTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizzes that = (KarutaQuizzes) o;

        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return "KarutaQuizzes{" +
                "values=" + values +
                '}';
    }
}
