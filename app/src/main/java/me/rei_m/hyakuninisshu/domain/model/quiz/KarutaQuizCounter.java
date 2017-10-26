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

public class KarutaQuizCounter implements ValueObject {

    private final int totalCount;

    private final int answeredCount;

    public KarutaQuizCounter(int totalCount, int answeredCount) {
        this.totalCount = totalCount;
        this.answeredCount = answeredCount;
    }

    public String value() {
        return String.valueOf(answeredCount + 1) + " / " + String.valueOf(totalCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KarutaQuizCounter that = (KarutaQuizCounter) o;

        return totalCount == that.totalCount &&
                answeredCount == that.answeredCount;
    }

    @Override
    public int hashCode() {
        int result = totalCount;
        result = 31 * result + answeredCount;
        return result;
    }

    @Override
    public String toString() {
        return "KarutaQuizCounter{" +
                "totalCount=" + totalCount +
                ", answeredCount=" + answeredCount +
                '}';
    }
}
