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

import me.rei_m.hyakuninisshu.domain.ValueObject;

/**
 * 問題全体の解答結果集計.
 */
public class KarutaQuizzesResultSummary implements ValueObject {

    private final int quizCount;

    private final int correctCount;

    private final float averageAnswerSec;

    public KarutaQuizzesResultSummary(int quizCount,
                                      int correctCount,
                                      float averageAnswerSec) {
        this.quizCount = quizCount;
        this.correctCount = correctCount;
        this.averageAnswerSec = averageAnswerSec;
    }

    /**
     * @return 問題数
     */
    public int quizCount() {
        return quizCount;
    }

    /**
     * @return 正解数
     */
    public int correctCount() {
        return correctCount;
    }

    /**
     * @return 平均解答時間
     */
    public float averageAnswerSec() {
        return averageAnswerSec;
    }

    /**
     * @return 表示向けスコア
     */
    public String score() {
        return correctCount + "/" + quizCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizzesResultSummary that = (KarutaQuizzesResultSummary) o;

        return quizCount == that.quizCount &&
                correctCount == that.correctCount &&
                Float.compare(that.averageAnswerSec, averageAnswerSec) == 0;
    }

    @Override
    public int hashCode() {
        int result = quizCount;
        result = 31 * result + correctCount;
        result = 31 * result + (averageAnswerSec != +0.0f ? Float.floatToIntBits(averageAnswerSec) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizResultSummary{" +
                "quizCount=" + quizCount +
                ", correctCount=" + correctCount +
                ", averageAnswerSec=" + averageAnswerSec +
                '}';
    }
}
