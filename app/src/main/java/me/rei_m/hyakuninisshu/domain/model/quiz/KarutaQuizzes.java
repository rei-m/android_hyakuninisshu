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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;

/**
 * 問題のコレクション.
 */
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

    /**
     * @return 解答済みの問題のうち、間違えた問題の歌のID
     */
    public KarutaIds wrongKarutaIds() {
        List<KarutaIdentifier> karutaIdentifiers = new ArrayList<>();
        for (KarutaQuiz karutaQuiz : values) {
            KarutaQuizResult result = karutaQuiz.result();
            if (result != null && !result.judgement().isCorrect()) {
                karutaIdentifiers.add(result.judgement().karutaId());
            }
        }
        return new KarutaIds(karutaIdentifiers);
    }

    /**
     * @return 解答結果を集計する
     * @throws IllegalStateException 未解答の問題があった場合
     */
    public KarutaQuizzesResultSummary resultSummary() throws IllegalStateException {
        final int quizCount = values.size();

        if (quizCount == 0) {
            return new KarutaQuizzesResultSummary(0, 0, 0);
        }

        long totalAnswerTimeMillSec = 0;

        int collectCount = 0;

        for (KarutaQuiz karutaQuiz : values) {
            KarutaQuizResult result = karutaQuiz.result();
            if (result == null) {
                throw new IllegalStateException("Training is not finished.");
            }

            totalAnswerTimeMillSec += result.answerTime();
            if (result.judgement().isCorrect()) {
                collectCount++;
            }
        }

        final float averageAnswerTime = totalAnswerTimeMillSec / (float) quizCount / (float) TimeUnit.SECONDS.toMillis(1);

        return new KarutaQuizzesResultSummary(quizCount, collectCount, averageAnswerTime);
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
