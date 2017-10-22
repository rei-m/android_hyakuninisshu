/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

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
