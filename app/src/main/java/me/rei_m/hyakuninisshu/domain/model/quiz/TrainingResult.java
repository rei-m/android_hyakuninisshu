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

import me.rei_m.hyakuninisshu.domain.ValueObject;

/**
 * 百人一首練習の結果.
 */
public class TrainingResult implements ValueObject {

    private final KarutaQuizzesResultSummary resultSummary;

    public TrainingResult(@NonNull KarutaQuizzesResultSummary resultSummary) {
        this.resultSummary = resultSummary;
    }

    /**
     * @return 問題の件数
     */
    public int quizCount() {
        return resultSummary.quizCount();
    }

    /**
     * @return 正解の件数
     */
    public int correctCount() {
        return resultSummary.correctCount();
    }

    /**
     * @return 平均解答時間
     */
    public float averageAnswerTime() {
        return resultSummary.averageAnswerTime();
    }

    /**
     * @return 練習を再開可能か
     */
    public boolean canRestartTraining() {
        return resultSummary.correctCount() != resultSummary.quizCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingResult that = (TrainingResult) o;

        return resultSummary.equals(that.resultSummary);

    }

    @Override
    public int hashCode() {
        return resultSummary.hashCode();
    }

    @Override
    public String toString() {
        return "TrainingResult{" +
                "resultSummary=" + resultSummary +
                '}';
    }
}
